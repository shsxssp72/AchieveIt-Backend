common_modification_permissions: list = [
    'user_role_modification',
    'user_privilege_modification',
    'project_info_modification',
    'member_list_modification',
    'function_list_modification',
    'project_status_modification',
    'working_hour_verification',
    'risk_modification'
]
device_modification_permission: list = [
    'project_device_modification', 'project_device_check', 'project_device_tenancy',
]

access_permission: list = [
    'user_role_access', 'user_privilege_access', 'project_info_access', 'member_list_access', 'function_list_access',
    'project_status_access', 'project_device_access', 'risk_access'
]
changeable_access_permission: list = [
    'issue_tracker_access', 'working_hour_access', 'project_git_access', 'mail_list_access', 'file_system_access'
]
changeable_modification_permission: list = [
    'working_hour_modification',
    'project_git_modification',
    'mail_list_modification',
    'file_system_modification',
    'issue_tracker_modification',
]
global_role_permission: dict = {
    'ProjectSuperior': access_permission + ['project_status_modification'],
    'ConfigurationManager': access_permission + device_modification_permission + ['project_status_modification'],
    'QaManager': access_permission + ['member_list_modification', 'user_role_modification', ],
    'EpgManager': access_permission + ['member_list_modification', 'user_role_modification', ],
    'ProjectManager': access_permission + common_modification_permissions + changeable_access_permission,
    'CommonUser': [],
}
project_role_permission: dict = {
    'DevelopmentLeader': access_permission,
    'QaLeader': access_permission,
    'DevelopmentStaff': [
        'user_role_access', 'project_info_access', 'member_list_access', 'function_list_access', 'project_status_access'
    ],
    'QaStaff': [
        'user_role_access', 'project_info_access', 'member_list_access', 'function_list_access', 'project_status_access'
    ],
    'EPG': [
        'user_role_access', 'project_info_access', 'member_list_access', 'function_list_access', 'project_status_access'
    ],
    'PropertyAdmin': access_permission + device_modification_permission,
}
permission_name_id_map: dict = {
    'user_role_modification': 294208645279776768,
    'user_role_access': 294208653534167040,
    'user_privilege_modification': 294208655207694336,
    'user_privilege_access': 294208659850788864,
    'project_info_modification': 294208667916435456,
    'project_info_access': 294208669929701376,
    'member_list_modification': 294208673507442688,
    'member_list_access': 294208679081672704,
    'function_list_modification': 294208686060994560,
    'function_list_access': 294208689034756096,
    'project_status_modification': 294208696483840000,
    'project_status_access': 294208699071725568,
    'issue_tracker_modification': 294208701688971264,
    'issue_tracker_access': 294208702120984576,
    'working_hour_modification': 294208708563435520,
    'working_hour_access': 294208716280954880,
    'working_hour_verification': 294208717023346688,
    'project_device_modification': 294208725156102144,
    'project_device_access': 294208726338895872,
    'project_device_check': 294208726858989568,
    'project_device_tenancy': 294208727806902272,
    'project_git_modification': 294208731082653696,
    'project_git_access': 294208732491939840,
    'mail_list_modification': 294208733922197504,
    'mail_list_access': 294208740175904768,
    'file_system_modification': 294208748191219712,
    'file_system_access': 294208755745161216,
    'risk_access': 294266309498109952,
    'risk_modification': 294266312480260096
}
global_role_name_id_map: dict = {
    'ProjectManager': 290070571767562241,
    'ProjectSuperior': 290082104513921025,
    'CommonUser': 290087899385298945,
    'ConfigurationManager': 290104426675306497,
    'QaManager': 294225641811738624,
    'EpgManager': 294225649508286464,
}
project_role_name_id_map: dict = {
    'DevelopmentLeader': 290089467161608193,
    'QaLeader': 294226508208144384,
    'DevelopmentStaff': 294226509294469120,
    'QaStaff': 294226515434930176,
    'EPG': 294226523576074240,
    'PropertyAdmin': 294226531868213248,
}
#### SQL
project_user_permission_relation: str = '''INSERT INTO ProjectUserPermissionRelation VALUES ({project_id},'{user_id}',{permission_id},1);'''
project_role_permission_relation: str = '''INSERT INTO ProjectRolePermissionRelation VALUES ({project_role_id},{permission_id});'''
global_role_permission_relation: str = '''INSERT INTO GlobalRolePermissionRelation VALUES ({project_role_id},{permission_id});'''


def add_global_role_permission(user_id: str, global_role_name: str) -> None:
    for i in global_role_permission[global_role_name]:
        print(project_user_permission_relation.format(project_id='NULL', user_id=user_id,
                                                      permission_id=permission_name_id_map[i]))


def generate_project_role_permission_relation() -> None:
    for role_name, role_id in project_role_name_id_map.items():
        for permission_name in project_role_permission[role_name]:
            print(project_role_permission_relation.format(project_role_id=role_id,
                                                          permission_id=permission_name_id_map[permission_name]))


def generate_global_role_permission_relation() -> None:
    for role_name, role_id in global_role_name_id_map.items():
        for permission_name in global_role_permission[role_name]:
            print(global_role_permission_relation.format(project_role_id=role_id,
                                                         permission_id=permission_name_id_map[permission_name]))


if __name__ == '__main__':
    add_global_role_permission('SYKJ-2019-1215-0000', 'EpgManager')
    # generate_project_role_permission_relation()
    # generate_global_role_permission_relation()
