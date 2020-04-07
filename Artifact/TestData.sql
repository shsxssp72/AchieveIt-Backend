INSERT INTO Project
VALUES ('2020-4577-D-01', 'Developing', 'SimpleManage开发', 'CS1123616462', '2019-12-01', '2020-06-01',
        'SYKJ-20180101-0000', '设计，开发，测试, 交付',
        'springboot', 290084263116668929, 'Manage');
INSERT INTO GlobalRole
VALUES (290070571767562241, 'ProjectManager');
INSERT INTO GlobalRole
VALUES (290082104513921025, 'ProjectSuperior');
INSERT INTO GlobalRole
VALUES (290087899385298945, 'CommonUser');
INSERT INTO GlobalRole
VALUES (290104426675306497, 'ConfigurationManager');
INSERT INTO GlobalRole
VALUES (294225641811738624, 'QaManager');
INSERT INTO GlobalRole
VALUES (294225649508286464, 'EpgManager');

INSERT INTO ProjectRole
VALUES (290089467161608193, 'DevelopmentLeader');
INSERT INTO ProjectRole
VALUES (294226508208144384, 'TestLeader');
INSERT INTO ProjectRole
VALUES (294226508208155935, 'TestStaff');
INSERT INTO ProjectRole
VALUES (294226508209937573, 'ProjectManager');
INSERT INTO ProjectRole
VALUES (294226509294469120, 'DevelopmentStaff');
INSERT INTO ProjectRole
VALUES (294226515434930176, 'QaStaff');
INSERT INTO ProjectRole
VALUES (294226523576074240, 'EPG');
INSERT INTO ProjectRole
VALUES (294226531868213248, 'PropertyAdmin');

# Note that default password is "password"
INSERT INTO UserInfo
VALUES ('SYKJ-20200101-0000', 287991808133169153, 'jun28@pingmao.net',
        '14f8b06e3e4067752ede17c643a3a7989dd70122bb579bed61355231394bafd2cf44866116e6f5ce3c6e5a5075b98b3c4c603124c9999db421fe4074c2f28395',
        '7gIlA4I5mQsL0vNABmChxrdH7ukXqpGgAjJGNjsIPWFeLiZOsxkOFJBtm8iVocjo', 290070571767562241);# ProjectManager
INSERT INTO UserInfo
VALUES ('SYKJ-20180101-0000', 287991808141557768, 'xiuying41@leizhu.cn',
        'd7aed848ebf184b143899f5270fdcb3a30d3c92e112292d98b89f385594d24860c42c94b3220f6090ce45c4772d03e3b41dac9701f3aae7500ba5452a44b78f2',
        '053Vbqrqmg9kWPPAc4xzPoIjdjkiOSqAecrRyAOeMtjrVEunyOsvY7AyN4UbHE1l', 290082104513921025);# ProjectSuperior
INSERT INTO UserInfo
VALUES ('SYKJ-20200201-0000', 287991808141557766, 'jie62@hotmail.com',
        'bd1364cb4ef7310fb0d093419c10c9396c63e7fef26bba5f57177758d802dba74b52a2a8da1539282c94cafcf7dbe350392cc7e94b9b3b6d1dfa8fb66fb333a0',
        'tr5vreNsQ2yhQVXGJye7zLWo4MHhsNWka9n7MpYLOFfNNq7vohJwOlNXUHLHOQ8y', 290087899385298945);# CommonUser
INSERT INTO UserInfo
VALUES ('SYKJ-20200201-0001', 287991808128974852, 'mpan@hotmail.com',
        '433f40754fea629bfc710571023d45be5b73ce120185c06f3636c81959103e07f114d1c45f129b3f4d46a36f5fa9dcc91316e620368169f033e12c4dff90fe1c',
        '3NZYThYNixfCKSwlrFaQpKLvOGo2A8plImseS0ie8ZZScqpFc7P4ht4TMmRXBRPF', 290104426675306497);# ConfigurationManager

INSERT INTO UserInfo
VALUES ('SYKJ-20200205-0000', 287991808158334976, 'juan32@gmail.com',
        'e2b918d0f7f9802f83acc50e53684ed4ff90993d64e1c56934e297cc331693a78dcaeb82de7acf00faeb3cd4275122ffe81d6ac1c1861c112e78a2b93d0730f2',
        'GBiwBraLLTuqgmAJNyjOwIfVVjXSOVNauepPUFqXYgoZlXKtUpBsPYNQabQXBbAD', 290087899385298945);# CommonUser

INSERT INTO UserInfo
VALUES ('SYKJ-20191201-0000', 287991808149946372, 'gangyao@hotmail.com',
        'ab66e3e6bb5d1f57c2a81c6b5e28ea737bc716ab1edb3751d870f316992b7d48e0288c0aa89aecb5f80a1cc48928e7e029bfec7e4c4f2f8428d7c9fe677f1ed2',
        'VxnOxOUGulwlsdKelBWwbMvyOcjKTzUIROSyAbTGmiJrHAsLgXGyClXUgcxnXXxO', 294225641811738624);# QaManager
INSERT INTO UserInfo
VALUES ('SYKJ-20191215-0000', 287991808175112192, 'gang66@guiyingguo.cn',
        '0576351aa6f8870ee98c6d3fdfb84720c8732cbd2af27cd0fdd5da1914a5b0c31a5a6e6f5766d160d11e4c5395b6ea2dba8e22a1fae5102379f5b2140d1ee3c2',
        'HEApCEjvQYnoxAGkMjmVSTqVnkXbYXObzxCfrxoStIkSHLJKglBgWMIoIPHUqzPj', 294225649508286464);# EpgManager

INSERT INTO BusinessField
VALUES (290084263116668929, 'Common');

INSERT INTO ProjectUserRelation
VALUES ('2020-4577-D-01', 'SYKJ-20200201-0000', 'SYKJ-20200101-0000', 290089467161608193);
INSERT INTO ProjectUserRelation
VALUES ('2020-4577-D-01', 'SYKJ-20200101-0000', 'SYKJ-20200101-0000', 294226508209937573);

# INSERT INTO Risk
# VALUES (290089816471633921, '2020-4577-D-01', 'PS', '需求变更', 'H', 'H', '沟通', '已识别', 'SYKJ-20200201-0000', '每周1次');
INSERT INTO Risk
VALUES ('RS-20200101-0000', '2020-4577-D-01', 'PS', '需求变更', 'H', 'H', '沟通', '已识别', 'SYKJ-20200101-0000', '每周1次');
INSERT INTO RiskRelatedPeople
VALUES ('RS-20200101-0000', 'SYKJ-20200201-0000');

INSERT INTO DeviceInfo
VALUES (290102211596255233, '部署服务器', 'LentOut');

INSERT INTO DeviceTenancy
VALUES (290103260491022337, '2020-4577-D-01', 290102211596255233, '2020-01-01', '2020-05-01', 'SYKJ-20200201-0001');

INSERT INTO ProjectFunction
VALUES (290114321369792513, '2020-4577-D-01', '001', 290114321369792513, '界面');

INSERT INTO WorkingHour
VALUES (290110995962003457, '界面', 'SYKJ-20200201-0000', '2020-4577-D-01', 290113610967941121, 290114321369792513,
        '2020-02-05', '2020-02-06', FALSE);


INSERT INTO Permission
VALUES (294208645279776768, 'user_role_modification');
INSERT INTO Permission
VALUES (294208653534167040, 'user_role_access');
INSERT INTO Permission
VALUES (294208655207694336, 'user_privilege_modification');
INSERT INTO Permission
VALUES (294208659850788864, 'user_privilege_access');
INSERT INTO Permission
VALUES (294208667916435456, 'project_info_modification');
INSERT INTO Permission
VALUES (294208669929701376, 'project_info_access');
INSERT INTO Permission
VALUES (294208673507442688, 'member_list_modification');
INSERT INTO Permission
VALUES (294208679081672704, 'member_list_access');
INSERT INTO Permission
VALUES (294208686060994560, 'function_list_modification');
INSERT INTO Permission
VALUES (294208689034756096, 'function_list_access');
INSERT INTO Permission
VALUES (294208696483840000, 'project_status_modification');
INSERT INTO Permission
VALUES (294208699071725568, 'project_status_access');
INSERT INTO Permission
VALUES (294208701688971264, 'issue_tracker_modification');
INSERT INTO Permission
VALUES (294208702120984576, 'issue_tracker_access');
INSERT INTO Permission
VALUES (294208708563435520, 'working_hour_modification');
INSERT INTO Permission
VALUES (294208716280954880, 'working_hour_access');
INSERT INTO Permission
VALUES (294208717023346688, 'working_hour_verification');
INSERT INTO Permission
VALUES (294208725156102144, 'project_device_modification');
INSERT INTO Permission
VALUES (294208726338895872, 'project_device_access');
INSERT INTO Permission
VALUES (294208726858989568, 'project_device_check');
INSERT INTO Permission
VALUES (294208727806902272, 'project_device_tenancy');
INSERT INTO Permission
VALUES (294208731082653696, 'project_git_modification');
INSERT INTO Permission
VALUES (294208732491939840, 'project_git_access');
INSERT INTO Permission
VALUES (294208733922197504, 'mail_list_modification');
INSERT INTO Permission
VALUES (294208740175904768, 'mail_list_access');
INSERT INTO Permission
VALUES (294208748191219712, 'file_system_modification');
INSERT INTO Permission
VALUES (294208755745161216, 'file_system_access');
INSERT INTO Permission
VALUES (294266309498109952, 'risk_access');
INSERT INTO Permission
VALUES (294266312480260096, 'risk_modification');

INSERT INTO ProjectRolePermissionRelation
VALUES (290089467161608193, 294208653534167040);
INSERT INTO ProjectRolePermissionRelation
VALUES (290089467161608193, 294208659850788864);
INSERT INTO ProjectRolePermissionRelation
VALUES (290089467161608193, 294208669929701376);
INSERT INTO ProjectRolePermissionRelation
VALUES (290089467161608193, 294208679081672704);
INSERT INTO ProjectRolePermissionRelation
VALUES (290089467161608193, 294208689034756096);
INSERT INTO ProjectRolePermissionRelation
VALUES (290089467161608193, 294208699071725568);
INSERT INTO ProjectRolePermissionRelation
VALUES (290089467161608193, 294208726338895872);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226508208144384, 294208653534167040);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226508208144384, 294208659850788864);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226508208144384, 294208669929701376);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226508208144384, 294208679081672704);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226508208144384, 294208689034756096);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226508208144384, 294208699071725568);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226508208144384, 294208726338895872);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226509294469120, 294208653534167040);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226509294469120, 294208669929701376);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226509294469120, 294208679081672704);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226509294469120, 294208689034756096);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226509294469120, 294208699071725568);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226515434930176, 294208653534167040);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226515434930176, 294208669929701376);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226515434930176, 294208679081672704);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226515434930176, 294208689034756096);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226515434930176, 294208699071725568);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226523576074240, 294208653534167040);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226523576074240, 294208669929701376);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226523576074240, 294208679081672704);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226523576074240, 294208689034756096);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226523576074240, 294208699071725568);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208653534167040);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208659850788864);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208669929701376);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208679081672704);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208689034756096);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208699071725568);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208726338895872);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208725156102144);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208726858989568);
INSERT INTO ProjectRolePermissionRelation
VALUES (294226531868213248, 294208727806902272);

INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208653534167040);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208659850788864);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208669929701376);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208679081672704);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208689034756096);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208699071725568);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208726338895872);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294266309498109952);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208645279776768);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208655207694336);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208667916435456);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208673507442688);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208686060994560);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208696483840000);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208717023346688);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294266312480260096);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208701688971264);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208702120984576);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208716280954880);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208732491939840);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208740175904768);
INSERT INTO GlobalRolePermissionRelation
VALUES (290070571767562241, 294208755745161216);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294208653534167040);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294208659850788864);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294208669929701376);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294208679081672704);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294208689034756096);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294208699071725568);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294208726338895872);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294266309498109952);
INSERT INTO GlobalRolePermissionRelation
VALUES (290082104513921025, 294208696483840000);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208653534167040);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208659850788864);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208669929701376);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208679081672704);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208689034756096);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208699071725568);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208726338895872);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294266309498109952);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208725156102144);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208726858989568);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208727806902272);
INSERT INTO GlobalRolePermissionRelation
VALUES (290104426675306497, 294208696483840000);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208653534167040);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208659850788864);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208669929701376);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208679081672704);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208689034756096);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208699071725568);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208726338895872);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294266309498109952);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208673507442688);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208645279776768);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225641811738624, 294208696483840000);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208653534167040);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208659850788864);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208669929701376);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208679081672704);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208689034756096);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208699071725568);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208726338895872);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294266309498109952);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208673507442688);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208645279776768);
INSERT INTO GlobalRolePermissionRelation
VALUES (294225649508286464, 294208696483840000);

INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208653534167040, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208659850788864, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208669929701376, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208679081672704, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208689034756096, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208699071725568, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208726338895872, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294266309498109952, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208645279776768, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208655207694336, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208667916435456, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208673507442688, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208686060994560, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208696483840000, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208717023346688, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294266312480260096, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208701688971264, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208702120984576, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208716280954880, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208732491939840, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208740175904768, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200101-0000', 294208755745161216, 1);

INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294208653534167040, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294208659850788864, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294208669929701376, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294208679081672704, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294208689034756096, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294208699071725568, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294208726338895872, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294266309498109952, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20180101-0000', 294208696483840000, 1);

INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208653534167040, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208659850788864, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208669929701376, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208679081672704, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208689034756096, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208699071725568, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208726338895872, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294266309498109952, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208673507442688, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208645279776768, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1201-0000', 294208696483840000, 1);

INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208653534167040, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208659850788864, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208669929701376, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208679081672704, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208689034756096, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208699071725568, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208726338895872, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294266309498109952, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208673507442688, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208645279776768, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-2019-1215-0000', 294208696483840000, 1);

INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208653534167040, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208659850788864, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208669929701376, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208679081672704, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208689034756096, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208699071725568, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208726338895872, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294266309498109952, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208725156102144, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208726858989568, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208727806902272, 1);
INSERT INTO ProjectUserPermissionRelation
VALUES (NULL, 'SYKJ-20200201-0001', 294208696483840000, 1);

INSERT INTO ActivityType
VALUES (299308584200568833, '工程活动', '需求开发');
INSERT INTO ActivityType
VALUES (299308584200568834, '工程活动', '设计');
INSERT INTO ActivityType
VALUES (290113610967941121, '工程活动', '编码');
INSERT INTO ActivityType
VALUES (299308584200568836, '工程活动', '单体测试');
INSERT INTO ActivityType
VALUES (299308584200568837, '工程活动', '集成测试');
INSERT INTO ActivityType
VALUES (299308584200568838, '工程活动', '系统测试');
INSERT INTO ActivityType
VALUES (299308584200568839, '工程活动', '交付');
INSERT INTO ActivityType
VALUES (299308584200568840, '工程活动', '维护');
INSERT INTO ActivityType
VALUES (299308584200568841, '管理活动', '范围管理');
INSERT INTO ActivityType
VALUES (299308584200568842, '管理活动', '计划与调整');
INSERT INTO ActivityType
VALUES (299308584200568843, '管理活动', '监控与分析');
INSERT INTO ActivityType
VALUES (299308584200568844, '管理活动', '联络与沟通');
INSERT INTO ActivityType
VALUES (299308584200568845, '外包活动', '外包管理');
INSERT INTO ActivityType
VALUES (299308584200568846, '外包活动', '外包验收');
INSERT INTO ActivityType
VALUES (299308584200568847, '外包活动', '外包支持');
INSERT INTO ActivityType
VALUES (299308584200568848, '支持活动', '配置管理');
INSERT INTO ActivityType
VALUES (299308584200568849, '支持活动', 'QA 审计');
INSERT INTO ActivityType
VALUES (299308584200568850, '支持活动', '会议报告总结');
INSERT INTO ActivityType
VALUES (299308584200568851, '支持活动', '培训');
INSERT INTO ActivityType
VALUES (299308584200568852, '支持活动', '其他');