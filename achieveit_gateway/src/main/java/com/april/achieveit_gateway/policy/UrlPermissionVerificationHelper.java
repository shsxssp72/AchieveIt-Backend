package com.april.achieveit_gateway.policy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UrlPermissionVerificationHelper
{
    @Value("${local.url-permission-constant-map-file}")
    private String URL_PERMISSION_CONSTANT_MAP_FILE;
    @Value("${local.url-permission-variable-map-file}")
    private String URL_PERMISSION_VARIABLE_MAP_FILE;
    /*
    "${uri}":{
        "${method}":{
            "permission":"${permission}"
            "projectIdLocation":"${projectIdLocation}"
        }
    }
    */
    private PatriciaTrie<HashMap<String,HashMap<String,String>>> constantUrlPermissionMap;

    /*
    ${uri-prefix}:{
        [
            "${uri}":{
                "${method}":{
                    "permission":"${permission}"
                    "projectIdLocation":"${projectIdLocation}"
                }
            }
        ]
    }
    */
    private PatriciaTrie<TreeMap<String,HashMap<String,HashMap<String,String>>>> variableUrlPermissionMap;
    /*
        "${uri}": ${compiled-pattern}
    }
    */
    private PatriciaTrie<Pattern> variableUrlPattern;
    private static Set<String> bodyProjectIdLocation=Set.of("project_id",
                                                            "referred_project_id");
    private static String urlProjectIdAnchor="([A-Z0-9\\-]+)";

    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @PostConstruct
    private void Init()
    {
        Resource resource=new ClassPathResource(URL_PERMISSION_CONSTANT_MAP_FILE);
        constantUrlPermissionMap=objectMapper.readValue(resource.getInputStream(),
                                                        new TypeReference<PatriciaTrie<HashMap<String,HashMap<String,String>>>>()
                                                        {
                                                        });
        resource=new ClassPathResource(URL_PERMISSION_VARIABLE_MAP_FILE);
        var innerVariableUrlPermissionMap=objectMapper.readValue(resource.getInputStream(),
                                                                 new TypeReference<HashMap<String,HashMap<String,HashMap<String,String>>>>()
                                                                 {
                                                                 });
        variableUrlPermissionMap=new PatriciaTrie<>();
        variableUrlPattern=new PatriciaTrie<>();
        for(var entry: innerVariableUrlPermissionMap.entrySet())
        {
            variableUrlPattern.put(entry.getKey(),
                                   Pattern.compile(entry.getKey()));

            var prefixKey=entry.getKey()
                    .substring(0,
                               entry.getKey()
                                       .indexOf(urlProjectIdAnchor));
            variableUrlPermissionMap.computeIfAbsent(prefixKey,
                                                     k->new TreeMap<>());
            variableUrlPermissionMap.get(prefixKey)
                    .put(entry.getKey(),
                         entry.getValue());
        }
    }

    private interface VariableUrlCandidateHelper<T>
    {
        T Match(Map.Entry<String,HashMap<String,HashMap<String,String>>> entry,Matcher matcher,String requestPath,String requestMethod);

        T Fallback();
    }

    private class VariableUrlCandidateSearch<T>
    {
        T Search(String requestPath,String requestMethod,VariableUrlCandidateHelper<T> helper)
        {
            String selectKey=variableUrlPermissionMap.selectKey(requestPath);
            if(!requestPath.startsWith(selectKey))
                return helper.Fallback();
            var currentCandidateUrlProperties=variableUrlPermissionMap.get(selectKey);
            for(var entry: currentCandidateUrlProperties.entrySet())
            {
                Pattern pattern=variableUrlPattern.get(entry.getKey());
                Matcher matcher=pattern.matcher(requestPath);
                if(matcher.matches())
                    return helper.Match(entry,
                                        matcher,
                                        requestPath,
                                        requestMethod);
            }
            return helper.Fallback();
        }
    }


    public boolean isValidRequestPath(String requestPath,String requestMethod)
    {
        if(constantUrlPermissionMap.containsKey(requestPath)&&constantUrlPermissionMap.get(requestPath)
                .containsKey(requestMethod))
            return true;

        VariableUrlCandidateSearch<Boolean> candidateSearch=new VariableUrlCandidateSearch<>();
        return candidateSearch.Search(requestPath,
                                      requestMethod,
                                      new VariableUrlCandidateHelper<Boolean>()
                                      {
                                          @Override
                                          public Boolean Match(Map.Entry<String,HashMap<String,HashMap<String,String>>> entry,Matcher matcher,String requestPath,String requestMethod)
                                          {
                                              return true;
                                          }

                                          @Override
                                          public Boolean Fallback()
                                          {
                                              return false;
                                          }
                                      });
    }

    public Map<String,String> getCurrentRequestPathProperty(String requestPath,String requestMethod)
    {
        if(constantUrlPermissionMap.containsKey(requestPath)&&constantUrlPermissionMap.get(requestPath)
                .containsKey(requestMethod))
            return constantUrlPermissionMap.get(requestPath)
                    .get(requestMethod);
        else
        {
            VariableUrlCandidateSearch<Map<String,String>> candidateSearch=new VariableUrlCandidateSearch<>();
            return candidateSearch.Search(requestPath,
                                          requestMethod,
                                          new VariableUrlCandidateHelper<Map<String,String>>()
                                          {
                                              @Override
                                              public Map<String,String> Match(Map.Entry<String,HashMap<String,HashMap<String,String>>> entry,Matcher matcher,String requestPath,String requestMethod)
                                              {
                                                  var entryValue=entry.getValue();
                                                  return entryValue.getOrDefault(requestMethod,
                                                                                 null);
                                              }

                                              @Override
                                              public Map<String,String> Fallback()
                                              {
                                                  return null;
                                              }
                                          });
        }
    }

    public String ParseProjectId(String requestPath,String requestMethod,String requestBody)
    {
        //TODO Variable version
        Map<String,String> currentRequestPathProperty=getCurrentRequestPathProperty(requestPath,
                                                                                    requestMethod);
        String projectIdLocation=currentRequestPathProperty.get("projectIdLocation");
        ProjectIdParser parser;
        if(projectIdLocation.equals("body"))
            parser=new BodyProjectIdParser();
        else if(projectIdLocation.equals("url"))
            parser=new UrlProjectIdParser();
        else
            return null;
        return parser.ParseProjectId(requestPath,
                                     requestMethod,
                                     requestBody);
    }

    private interface ProjectIdParser
    {
        String ParseProjectId(String requestPath,String requestMethod,String requestBody);
    }

    private class UrlProjectIdParser implements ProjectIdParser
    {
        @Override
        public String ParseProjectId(String requestPath,String requestMethod,String requestBody)
        {
            VariableUrlCandidateSearch<String> candidateSearch=new VariableUrlCandidateSearch<>();
            return candidateSearch.Search(requestPath,
                                          requestMethod,
                                          new VariableUrlCandidateHelper<String>()
                                          {
                                              @Override
                                              public String Match(Map.Entry<String,HashMap<String,HashMap<String,String>>> entry,Matcher matcher,String requestPath,String requestMethod)
                                              {
                                                  return matcher.group(1);
                                              }

                                              @Override
                                              public String Fallback()
                                              {
                                                  return null;
                                              }
                                          });
        }
    }


    private class BodyProjectIdParser implements ProjectIdParser
    {
        @SneakyThrows
        @Override
        public String ParseProjectId(String requestPath,String requestMethod,String requestBody)
        {
            Map<String,String> bodyMap=objectMapper.readValue(requestBody,
                                                                      new TypeReference<Map<String,String>>()
                                                                      {
                                                                      });
            for(String item:bodyProjectIdLocation)
            {
                if(bodyMap.containsKey(item))
                    return bodyMap.get(item);
            }
            return null;
        }
    }

}
