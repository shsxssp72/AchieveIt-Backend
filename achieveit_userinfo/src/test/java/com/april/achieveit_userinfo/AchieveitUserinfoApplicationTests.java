package com.april.achieveit_userinfo;

import com.april.achieveit_common.utility.SnowFlakeIdGenerator;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.List;


class AchieveitUserinfoApplicationTests
{
    SnowFlakeIdGenerator snowFlakeIdGenerator=new SnowFlakeIdGenerator(0,
                                                                       0);

    @SneakyThrows
    @Test
    public void makeData()
    {
        String projectSqlTemplate="'%s', '%s', '%s', '%s', '%s', '%s',\n'%s', '%s',\n'%s', %d, '%s');";
        String globalRoleGenerateSqlTemplate="INSERT INTO GlobalRole\nVALUES (%d, '%s');";
        String projectRoleGenerateSqlTemplate="INSERT INTO ProjectRole\nVALUES (%d, '%s');";

        List<String> globalRoles=List.of("ProjectSuperior",
                                         "ConfigurationManager",
                                         "QaManager",
                                         "EpgManager",
                                         "ProjectManager",
                                         "CommonUser");
        List<String> projectRoles=List.of(
                "DevelopmentLeader","QaLeader","DevelopmentStaff","QaStaff","EPG","PropertyAdmin"
        );
        List<String> permissions=List.of("user_role_modification",
                                         "user_role_access",
                                         "user_privilege_modification",
                                         "user_privilege_access",
                                         "project_info_modification",
                                         "project_info_access",
                                         "member_list_modification",
                                         "member_list_access",
                                         "function_list_modification",
                                         "function_list_access",
                                         "project_status_modification",
                                         "project_status_access",
                                         "issue_tracker_modification",
                                         "issue_tracker_access",
                                         "working_hour_modification",
                                         "working_hour_access",
                                         "working_hour_verification",
                                         "project_device_modification",
                                         "project_device_access",
                                         "project_device_check",
                                         "project_device_tenancy",
                                         "project_git_modification",
                                         "project_git_access",
                                         "mail_list_modification",
                                         "mail_list_access",
                                         "file_system_modification",
                                         "file_system_access");
        String permissionSqlTemplate="INSERT INTO Permission\nVALUES (%d, '%s');";

        for(var i: projectRoles)
        {
            System.out.println(String.format(projectRoleGenerateSqlTemplate,
                                             snowFlakeIdGenerator.getNextId(),
                                             i));
            Thread.sleep(RandomUtils.nextInt(100, 2000));
        }

    }

}

