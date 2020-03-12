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
@RequestMapping(path="/project/risk")
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
    private ResponseContent assembleRiskAndSendEmail(String projectId,Map<String,String> params,ServiceCaller serviceCaller)
    {
        ResponseContent result=new ResponseContent();

        Risk risk=new Risk(params.getOrDefault("risk_id",
                                               null),
                           projectId,
                           params.get("risk_type"),
                           params.get("risk_description"),
                           params.get("risk_level"),
                           params.get("risk_impact"),
                           params.get("risk_countermeasure"),
                           params.get("risk_status"),
                           null,
                           params.get("risk_track_frequency"));
        List<String> riskRelatedPerson=objectMapper.readValue(params.get("risk_responsible_person"),
                                                              new TypeReference<List<String>>()
                                                              {
                                                              });

        serviceCaller.Invoke(risk,
                             riskRelatedPerson);

        result.setMessage(dependencyService.sendEmail());
        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @SneakyThrows
    @PostMapping(path="/{project_id}")
    public ResponseContent AddRisk(@PathVariable(name="project_id") String projectId,@RequestBody Map<String,String> params)
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
        List<String> queryResult=projectRiskService.GetRisk(projectId);

        result.setResult(queryResult);

        result.setStatus(ResponseContentStatus.SUCCESS);
        return result;
    }

    @PostMapping(path="/{project_id}/{risk_id}")
    public ResponseContent UpdateRisk(@PathVariable(name="project_id") String projectId,@PathVariable(name="risk_id") String riskId,@RequestBody Map<String,String> params)
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
