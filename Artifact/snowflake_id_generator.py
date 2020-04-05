from datetime import datetime


class SnowflakeIdGenerator(object):
    START_TIMESTAMP: int = int(datetime(2018, 1, 1, 0, 0, 0).timestamp() * 1000)
    SERIAL_BITS_LENGTH: int = 14
    MACHINE_BITS_LENGTH: int = 6
    DATA_CENTER_BITS_LENGTH: int = 2

    MAXIMUM_SERIAL_NUM = ~(-1 << SERIAL_BITS_LENGTH)
    MAXIMUM_MACHINE_NUM = ~(-1 << MACHINE_BITS_LENGTH)
    MAXIMUM_DATA_CENTER_NUM = ~(-1 << DATA_CENTER_BITS_LENGTH)

    SERIAL_OFFSET: int = 0
    MACHINE_OFFSET: int = SERIAL_OFFSET + SERIAL_BITS_LENGTH
    DATA_CENTER_OFFSET: int = MACHINE_OFFSET + MACHINE_BITS_LENGTH
    TIMESTAMP_OFFSET: int = DATA_CENTER_OFFSET + DATA_CENTER_BITS_LENGTH

    def __init__(self, data_center_id: int, machine_id: int):
        self.data_center_id: int = data_center_id
        self.machine_id: int = machine_id
        self.serial: int = 0
        self.last_time_stamp: int = int(datetime.now().timestamp() * 1000)

    def get_next_id(self) -> int:
        current_stamp: int = int(datetime.now().timestamp() * 1000)
        if current_stamp < self.last_time_stamp:
            raise RuntimeError("Clock move backwards. Quitting due to unacceptable fatal error.")

        if current_stamp == self.last_time_stamp:
            self.serial = (self.serial + 1) & SnowflakeIdGenerator.MAXIMUM_SERIAL_NUM
            if self.serial == 0:
                self.wait_until_next_millisecond()
                current_stamp = int(datetime.now().timestamp() * 1000)
        else:
            self.serial = 0
        self.last_time_stamp = current_stamp
        result: int = int(current_stamp - SnowflakeIdGenerator.START_TIMESTAMP) \
                      << SnowflakeIdGenerator.TIMESTAMP_OFFSET \
                      | self.data_center_id << SnowflakeIdGenerator.DATA_CENTER_OFFSET \
                      | self.machine_id << SnowflakeIdGenerator.MACHINE_OFFSET \
                      | self.serial << SnowflakeIdGenerator.SERIAL_OFFSET
        return result

    def wait_until_next_millisecond(self):
        current_stamp = datetime.now().timestamp() * 1000
        while current_stamp <= self.last_time_stamp:
            current_stamp = datetime.now()


if __name__ == '__main__':
    id_generator = SnowflakeIdGenerator(machine_id=0, data_center_id=0)
    for i in range(1, 200):
        print(id_generator.get_next_id())
