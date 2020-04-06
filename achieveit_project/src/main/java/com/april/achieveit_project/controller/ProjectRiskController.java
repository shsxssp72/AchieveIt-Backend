package com.april.achieveit_project.controller;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_project.entity.Risk;
import com.april.achieveit_project.service.DependencyService;
import com.april.achieveit_project.service.ProjectRiskService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/risk")
public class ProjectRiskController
{
    private static Logger logger=LoggerFactory.getLogger(ProjectRiskController.class);
    @Autowired
    private ProjectRiskService projectRiskService;
    @Autowired
    private DependencyService dependencyService;
    @Autowired
    private ObjectMapper objectMapper;

    private interface ServiceCaller
    {
        void Invoke(Risk risk,List<String> relatedPerson);
    }

    @SneakyThrows
    private ResponseContent assembleRiskAndSendEmail(String projectId,Map<String,Object> params,ServiceCaller serviceCaller)
    {
        ResponseContent result=new ResponseContent();

        Risk risk=new Risk((String)params.getOrDefault("risk_id",
                                                       null),
                           projectId,
                           (String)params.get("risk_type"),
                           (String)params.get("risk_description"),
                           (String)params.get("risk_level"),
                           (String)params.get("risk_impact"),
                           (String)params.get("risk_countermeasure"),
                           (String)params.get("risk_status"),
                           (String)params.get("risk_responsible_person"),
                           (String)params.get("risk_track_frequency"));
        List<String> riskRelatedPerson=objectMapper.convertValue(params.get("risk_related_person"),
                                                                 new TypeReference<List<String>>()
                                                                 {
                                                                 });

        serviceCaller.Invoke(risk,
                             riskRelatedPerson);

        result.setMessage(dependencyService.sendEmail("",
                                                      String.join(";",
                                                                  riskRelatedPerson),
                                                      ""));
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @PutMapping(path="/{project_id}")
    public ResponseContent AddRisk(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,Object> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());

        return assembleRiskAndSendEmail(projectId,
                                        params,
                                        (risk,riskRelatedPerson)->projectRiskService.NewRisk(risk,
                                                                                             riskRelatedPerson));
    }

    @GetMapping(path="/{project_id}")
    public ResponseContent GetRisk(@PathVariable(name="project_id") String projectId)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();
        List<Map<String,Object>> queryResult=projectRiskService.GetRisk(projectId);

        result.setResult(queryResult);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PutMapping(path="/{project_id}/{risk_id}")
    public ResponseContent UpdateRisk(@PathVariable(name="project_id") String projectId,@PathVariable(name="risk_id") String riskId,@RequestBody Map<String,Object> params)
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        params.put("risk_id",
                   riskId);
        return assembleRiskAndSendEmail(projectId,
                                        params,
                                        (risk,riskRelatedPerson)->projectRiskService.UpdateRisk(risk,
                                                                                                riskRelatedPerson));
    }

    @GetMapping("/riskType")
    public ResponseContent GetRiskType()
    {
        logger.info("Invoking :"+Thread.currentThread()
                .getStackTrace()[1].getMethodName());
        ResponseContent result=new ResponseContent();

        result.setResult("[\"PS\",\"PD\",\"ST\",\"CU\",\"DE\",\"TE\",\"BU\"]");

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }
}
