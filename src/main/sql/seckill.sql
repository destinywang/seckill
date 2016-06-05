-- 存储过程
DELIMITER $$ -- console ; 转换为$$
-- 定义存储过程
-- 参数：in：输入参数
-- out：输出参数
-- ROW_COUNT();返回上一条修改类型的SQL(INSERT, DELETE, UPDATE)的影响行数
-- row_count：0未修改数据，>0表示修改的行数，<0表示SQL错误或者未执行修改SQL
CREATE PROCEDURE seckill.execute_seckill
  (IN v_seckill_id BIGINT , IN v_phone BIGINT ,
  IN v_kill_time TIMESTAMP , OUT r_result INT)
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION ;
    INSERT IGNORE INTO success_killed
      (seckillId, user_phone, create_time)
      VALUES (v_seckill_id, v_phone, v_kill_time);
    SELECT ROW_COUNT() INTO insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK ;
      SET r_result = -1;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = -2;
    ELSE
      UPDATE seckill
      SET number = number - 1
      WHERE seckill_id = v_seckill_id
        AND end_time > v_kill_time
        AND start_time < v_kill_time
        AND number > 0;
      SELECT ROW_COUNT() INTO insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        SET r_result = 0;
      ELSE IF (insert_count < 0) THEN
        ROLLBACK ;
        SET r_result = -2;
      ELSE
        COMMIT ;
        set r_result = 1;
      END IF;
    END IF
  END ;
$$
-- 存储过程定义结束

DELIMITER ;
SET @r_result=-3;
CALL execute_seckill(1003, 11111111111, now(), @r_result);

--获取结果
SELECT @r_result;