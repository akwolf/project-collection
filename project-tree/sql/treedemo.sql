/*
Navicat MySQL Data Transfer

Source Server         : mysql5.5
Source Server Version : 50528
Source Host           : localhost:3307
Source Database       : treedemo

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2013-07-25 17:57:53
*/

SET FOREIGN_KEY_CHECKS=0;


-- ----------------------------
-- Table structure for `treedemo`
-- ----------------------------
DROP TABLE IF EXISTS `treedemo`;
CREATE TABLE `treedemo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPART_NAME` varchar(100) NOT NULL,
  `PARENT_ID` int(11) DEFAULT NULL,
  `DEPART_CODE` varchar(100) DEFAULT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `parent_fk` (`PARENT_ID`),
  CONSTRAINT `parent_fk` FOREIGN KEY (`PARENT_ID`) REFERENCES `treedemo` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treedemo
-- ----------------------------
INSERT INTO `treedemo` VALUES ('1', '总公司', null, '100', '2013-07-19 15:28:13');
INSERT INTO `treedemo` VALUES ('2', '广东分公司', '1', '10001000', '2013-07-19 15:30:27');
INSERT INTO `treedemo` VALUES ('3', '湖北分公司', '1', '10001001', '2013-07-19 15:30:47');
INSERT INTO `treedemo` VALUES ('4', '上海分公司', '1', '10001002', '2013-07-19 15:31:24');
INSERT INTO `treedemo` VALUES ('5', '武汉分公司', '3', '1000100010000', '2013-07-19 15:31:52');
INSERT INTO `treedemo` VALUES ('6', '广水分公司', '3', '1000100010001', '2013-07-19 15:32:19');
INSERT INTO `treedemo` VALUES ('7', '深圳分公司', '2', '1000100010002', '2013-07-19 15:32:46');

-- ----------------------------
-- Procedure structure for `ws_tree_get_by_parent_id`
-- ----------------------------
DROP PROCEDURE IF EXISTS `ws_tree_get_by_parent_id`;
DELIMITER ;;
CREATE DEFINER=`zhangh`@`%` PROCEDURE `ws_tree_get_by_parent_id`(IN `p_id` int)
BEGIN

DECLARE is_parent INT;

select p_id REGEXP '^-?[0-9]+$' into is_parent;

IF is_parent is NULL || is_parent = 0 || p_id < 1 THEN
	SET is_parent = 1 ;
ELSE
	SET is_parent = 0 ;
end if ;

SELECT hi.id AS id, DEPART_NAME AS name, parent_ID as parentId, lvl as level, COALESCE ((SELECT 0	FROM treedemo hl WHERE hl.parent_ID = ho.id	LIMIT 1	), 1) AS leaf 
  FROM 
  	(SELECT treedemo_hierarchy_connect_by_parent_eq_prior_id (id) AS id, CAST(@LEVEL AS SIGNED) AS lvl FROM( SELECT @start_with := 0, @id := @start_with, @LEVEL := 0) vars, treedemo 
 	 WHERE @id IS NOT NULL
	) ho JOIN treedemo hi ON hi.id = ho.id and CASE WHEN is_parent = 1 THEN parent_ID is null ELSE parent_ID = p_id END;

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `treedemo_hierarchy_connect_by_parent_eq_prior_id`
-- ----------------------------
DROP FUNCTION IF EXISTS `treedemo_hierarchy_connect_by_parent_eq_prior_id`;
DELIMITER ;;
CREATE DEFINER=`zhangh`@`%` FUNCTION `treedemo_hierarchy_connect_by_parent_eq_prior_id`(`value` INT) RETURNS int(11)
    READS SQL DATA
BEGIN
        DECLARE _id INT;
        DECLARE _parent INT;
        DECLARE _next INT;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET @id = NULL;

        SET _parent = @id;
        SET _id = -1;

        IF @id IS NULL THEN
                RETURN NULL;
        END IF;

        LOOP
                SELECT  MIN(id)
                INTO    @id
                FROM    treedemo
                WHERE   COALESCE(parent_id, 0) = _parent
                        AND id > _id;
                IF @id IS NOT NULL OR _parent = @start_with THEN
                        SET @level = @level + 1;
                        RETURN @id;
                END IF;
                SET @level := @level - 1;
                SELECT  id, COALESCE(parent_id, 1)
                INTO    _id, _parent
                FROM    treedemo
                WHERE   id = _parent;
        END LOOP;       
END
;;
DELIMITER ;
