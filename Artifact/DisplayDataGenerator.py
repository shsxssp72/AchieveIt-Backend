import random

username: dict = {
    'minjiang@gmail.com': 'SYKJ-20191015-7815',
    'yanlei@yahoo.com': 'SYKJ-20190415-7121',
    'yongcheng@pd.cn': 'SYKJ-20180801-7904',
    'guiyinghou@chaosong.cn': 'SYKJ-20190201-3577',
    'iqian@ping.cn': 'SYKJ-20191015-2574',
    'yanglin@91.cn': 'SYKJ-20191015-7311',
    'qiang90@gmail.com': 'SYKJ-20180801-2358',
    'hexiulan@yahoo.com': 'SYKJ-20191001-1893',
    'junlong@zou.cn': 'SYKJ-20190801-3952',
    'hanguiying@26.cn': 'SYKJ-20180801-8303',
    'pengfang@19.cn': 'SYKJ-20190415-7997',
    'wsu@leipeng.cn': 'SYKJ-20190801-3417',
    'yandeng@leiwu.cn': 'SYKJ-20191001-4024',
    'lipeng@hotmail.com': 'SYKJ-20180201-1699',
    'fjin@xiagang.cn': 'SYKJ-20180401-2295',
    'yongdu@yahoo.com': 'SYKJ-20180801-3666',
    'xiulanyao@gangjiang.cn': 'SYKJ-20181001-1300',
    'jieliang@hotmail.com': 'SYKJ-20180401-6060',
    'xiaoping@hotmail.com': 'SYKJ-20190815-7127',
    'guiyingdai@gmail.com': 'SYKJ-20190801-5244',
    'vding@hotmail.com': 'SYKJ-20190415-6442',
    'liliu@hotmail.com': 'SYKJ-20190815-9464',
    'mduan@gmail.com': 'SYKJ-20190401-1728',
    'yonglai@gmail.com': 'SYKJ-20181001-8899',
    'juan36@hu.cn': 'SYKJ-20190401-3986',
    'jie19@gmail.com': 'SYKJ-20190415-9827',
    'exia@zhuzheng.cn': 'SYKJ-20180201-4668',
    'gang66@guiyingguo.cn': 'SYKJ-20191215-0000',
    'ohuang@nazhu.com': 'SYKJ-20190801-9945',
    'gang73@32.cn': 'SYKJ-20180201-7830',
    'yuanxiulan@hotmail.com': 'SYKJ-20191015-7334',
}

project_ids: list = [
    '2020-7530-D-43',
    '2020-7559-D-60',
    '2020-7691-D-98',
]
customer_ids: list = [
    'CS1352608568', 'CS1350447349', 'CS1346125359', 'CS1345776352', 'CS1345197805', 'CS1340668799', 'CS1337867242',
    'CS1336562253', 'CS1335452822', 'CS1330384996', 'CS1321181553', 'CS1313681206', 'CS1310677519', 'CS1303239236',
    'CS1294061418', 'CS1283154490', 'CS1280865871', 'CS1276117532', 'CS1275008052', 'CS1274741748', 'CS1271379364',
    'CS1270479873', 'CS1263775822', 'CS1260498132', 'CS1254300389', 'CS1252071430', 'CS1251133545', 'CS1249891898',
    'CS1239099183', 'CS1236485424', 'CS1236375319', 'CS1227315075', 'CS1225981402', 'CS1224982267', 'CS1224290616',
    'CS1218079924', 'CS1217842323', 'CS1215560207', 'CS1215483417', 'CS1201972392', 'CS1201532786', 'CS1200851907',
    'CS1197518698', 'CS1192762538', 'CS1183480768', 'CS1177601784', 'CS1168728056', 'CS1166066179', 'CS1162998756',
    'CS1161970395', 'CS1161580517', 'CS1160936309', 'CS1156449413', 'CS1139939455', 'CS1134465497', 'CS1130348830',
    'CS1129850105', 'CS1124772051', 'CS1123616462', 'CS1111609728', 'CS1110517465', 'CS1109273674', 'CS1100254254',
    'CS1099642740', 'CS1099316703', 'CS1093111525', 'CS1087038911', 'CS1079348356', 'CS1077214068', 'CS1072239576',
    'CS1069240476', 'CS1062577176', 'CS1055890724', 'CS1053622958', 'CS1051492111', 'CS1047520827', 'CS1039719992',
    'CS1039248121', 'CS1028016400', 'CS1027707021', 'CS1026324078', 'CS1019691090', 'CS1017481441', 'CS1015685047', ]

INSERT_USER_SQL_TEMPLATE: str = '''INSERT INTO UserInfo VALUES ('{user_id}', {deprecated_outer_id}, '{username}', '{hashed_password}', '{salt}', {global_role_id});'''
NEW_PROJECT_SQL_TEMPLATE: str = '''INSERT INTO Project VALUES ('{project_id}', '{status}', '{project_name}', '{referred_outer_customer_id}', '{start_time}', '{end_time}', NULL, '{milestone}', '{technology}', 293116318507335680, '{main_function}');'''

if __name__ == '__main__':
    # id_generator = SnowflakeIdGenerator(0, 0)
    # md5_password: str = (hashlib.md5('password'.encode('utf8')).hexdigest())
    # for key, value in username.items():
    #     salt: str = ''.join(random.choice(string.ascii_letters) for i in range(64))
    #     real_salt: str = key + salt
    #     to_hash: str = md5_password + real_salt
    #     hashed_password: str = hashlib.sha3_512(to_hash.encode('utf8')).hexdigest()
    #     print(INSERT_USER_SQL_TEMPLATE.format(user_id=value,
    #                                           deprecated_outer_id=id_generator.get_next_id(),
    #                                           username=key,
    #                                           hashed_password=hashed_password,
    #                                           salt=salt,
    #                                           global_role_id=290087899385298945))
    i: int = 0
    for item in project_ids:
        print(NEW_PROJECT_SQL_TEMPLATE.format(project_id=item,
                                              status=random.choice(['Developing', 'Applied', 'Delievered', 'Archived']),
                                              project_name='test_project' + str(i),
                                              referred_outer_customer_id=random.choice(customer_ids),
                                              start_time=random.choice(['2018-02-01',
                                                                        '2018-02-15',
                                                                        '2018-04-01',
                                                                        '2018-04-15',
                                                                        '2018-08-01',
                                                                        '2018-08-15',
                                                                        '2018-10-01', ]),
                                              end_time=random.choice(['2018-10-15',
                                                                      '2019-02-01',
                                                                      '2019-04-01',
                                                                      '2019-04-15',
                                                                      '2019-08-01',
                                                                      '2019-08-15',
                                                                      '2019-10-01',
                                                                      '2019-10-15', ]),
                                              milestone='设计，开发，测试, 交付',
                                              technology=random.choice(
                                                  ['spring boot', 'asp.net core', 'django', 'flask', ]),
                                              main_function=random.choice([
                                                  'e-commerce', 'user management', 'library database'])))
        i += 1
