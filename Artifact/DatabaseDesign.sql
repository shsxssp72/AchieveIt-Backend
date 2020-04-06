DROP DATABASE IF EXISTS AchieveIt;
CREATE DATABASE AchieveIt;
USE AchieveIt;

CREATE TABLE BusinessField
(
    business_field_id          BIGINT PRIMARY KEY,
    business_field_description VARCHAR(255)
)
CREATE TABLE Project
(
    project_id                 CHAR(20) PRIMARY KEY,
    status                     CHAR(20), # - Applied,Initiated,Rejected,Developing,Delivered,Finished,Archived
    project_name               VARCHAR(255),
    referred_outer_customer_id CHAR(15),# Separated system
    scheduled_start_time       TIMESTAMP,
    scheduled_end_time         TIMESTAMP,
    referred_superior_id       CHAR(20),
    mile_stone                 VARCHAR(255),
    technology                 VARCHAR(255),
    referred_business_field_id BIGINT,
    main_function              VARCHAR(255)
);
CREATE TABLE ProjectMiscellaneous #For checking whether QA/EPG Manager && Org.Conf. Manager added people
(
    misc_id             BIGINT PRIMARY KEY,
    referred_project_id CHAR(20),
    key_field           VARCHAR(64),
    value_field         VARCHAR(255)
);
CREATE TABLE ProjectFunction
(
    function_id          BIGINT PRIMARY KEY,
    referred_project_id  CHAR(20),
    id_for_display       VARCHAR(20),
    superior_function_id BIGINT,
    description          VARCHAR(255)
);
CREATE TABLE GlobalRole # Use constant configuration
(
    global_role_id   BIGINT PRIMARY KEY,
    global_role_name VARCHAR(25)# Superior,ConfigurationManager,QaManager,EpgManager,ProjectManager,Ordinary
);
CREATE TABLE UserInfo
(
    user_id                      CHAR(20) PRIMARY KEY,
    referred_outer_user_id       BIGINT,# Separated system
    user_name                    VARCHAR(50),
    user_password                CHAR(128),
    user_salt                    CHAR(64),
    referred_user_global_role_id BIGINT# One user can only have one global role?
);
CREATE TABLE ProjectRole # Use constant configuration
(
    project_role_id   BIGINT PRIMARY KEY,
    project_role_name VARCHAR(25)# DevelopmentLeader,QaLeader,DevelopmentStaff,QaStaff,EPG,PropertyAdmin
);
CREATE TABLE ProjectUserRelation
(
    referred_project_id      CHAR(20),
    referred_user_id         CHAR(20),
    referred_superior_id     CHAR(20),
    referred_project_role_id BIGINT
);
CREATE TABLE ProjectUserPermissionRelation
(
    referred_project_id    CHAR(20),
    referred_user_id       CHAR(20),
    referred_permission_id BIGINT,
    permit_weight          INT
);
CREATE TABLE ActivityType # Use constant configuration
(
    activity_type_id    BIGINT PRIMARY KEY,
    level_1_description VARCHAR(20),
    level_2_description VARCHAR(20)
);
CREATE TABLE WorkingHour
(
    working_hour_id               BIGINT PRIMARY KEY,
    function_description_snapshot VARCHAR(255),
    referred_user_id              CHAR(20),
    referred_project_id           CHAR(20),
    referred_activity_type_id     BIGINT,
    referred_function_id          BIGINT,
    start_time                    TIMESTAMP,
    end_time                      TIMESTAMP,
    verified                      BOOL
);



CREATE TABLE Risk
(
    risk_id                 CHAR(16) PRIMARY KEY,
    referred_project_id     CHAR(20),
    risk_type               CHAR(20),
    risk_description        VARCHAR(255),
    risk_level              CHAR(10),
    risk_impact             VARCHAR(255),
    risk_countermeasure     VARCHAR(255),
    risk_status             CHAR(15),
    risk_responsible_person CHAR(20),
    risk_track_frequency    CHAR(20)# May not expressed by TIME
);
CREATE TABLE RiskRelatedPeople
(
    referred_risk_id           CHAR(16),
    referred_related_person_id CHAR(20)
);



CREATE TABLE DeviceInfo
(
    device_id     BIGINT PRIMARY KEY,
    device_name   VARCHAR(255),
    device_status CHAR(20) # Available, LentOut, Examining
);
CREATE TABLE DeviceTenancy
(
    tenancy_id                 BIGINT PRIMARY KEY,
    referred_project_id        CHAR(20),
    referred_device_id         BIGINT,
    tenancy_time               TIMESTAMP,
    scheduled_return_time      TIMESTAMP,
    referred_device_manager_id CHAR(20)
);
CREATE TABLE DeviceExamination
(
    examination_id     BIGINT PRIMARY KEY,
    referred_device_id BIGINT,
    examination_time   TIMESTAMP,
    referred_tester_id CHAR(20),
    test_result        VARCHAR(255)
);



CREATE TABLE Permission # Use constant configuration
(
    permission_id   BIGINT PRIMARY KEY,
    permission_name VARCHAR(50)
);
CREATE TABLE GlobalRolePermissionRelation
(
    referred_global_role_id BIGINT,
    referred_permission_id  BIGINT
);
CREATE TABLE ProjectRolePermissionRelation
(
    referred_project_role_id BIGINT,
    referred_permission_id   BIGINT
);


# DEPENDENCIES
CREATE TABLE OuterUsers
(
    user_id         BIGINT PRIMARY KEY,
    user_name       VARCHAR(50),
    user_email      VARCHAR(255),
    user_department VARCHAR(50),
    user_telephone  VARCHAR(15)
);
CREATE TABLE OuterCustomers
(
    customer_id             CHAR(15) PRIMARY KEY,
    referred_coordinator_id BIGINT,
    corporation_name        VARCHAR(255),
    customer_level          VARCHAR(10),
    customer_email          VARCHAR(255),
    customer_telephone      VARCHAR(15),
    customer_address        VARCHAR(255)
);


