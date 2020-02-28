---------------为了方便flowable使用定义的视图---------------------
---------------待办视图---------------------
CREATE OR REPLACE VIEW "public"."act_task_todo_list" AS
SELECT art.id_                                                                             AS task_id,
       art.name_                                                                           AS task_name,
       aren.name_                                                                          AS form_name,
       aren.tenant_id_                                                                     AS tenant_id,
       aren.business_key_                                                                  AS business_key,
       aren.proc_def_id_                                                                   AS process_definition_id,
       arp.name_                                                                           AS process_definition_name,
       aren.proc_inst_id_                                                                  AS process_instance_id,
       art.create_time_                                                                    AS start_time,
       aren.start_user_id_                                                                 AS initiator_id,
       (SELECT aiu.display_name_ FROM act_id_user aiu WHERE aiu.id_ = aren.start_user_id_) AS initiator_name,
       art.assignee_                                                                       AS assignee_id,
       ari.user_id_                                                                        AS link_user_id,
       aim.user_id_                                                                        AS candidate_user_id,
       ari.group_id_                                                                       AS gourp_id
FROM act_ru_task art
         INNER JOIN act_ru_execution aren ON art.proc_inst_id_ = aren.proc_inst_id_
         INNER JOIN act_re_procdef arp ON aren.proc_def_id_ = arp.id_
         LEFT JOIN act_ru_identitylink ari ON art.id_ = ari.task_id_
         LEFT JOIN act_id_membership aim ON ari.group_id_ = aim.group_id_
WHERE aren.business_key_ IS NOT NULL;
---------------已办视图---------------------
CREATE OR REPLACE VIEW "public"."act_task_complete_list" AS
SELECT aht.id_                                                      AS task_id,
       aht.name_                                                    AS task_name,
       aiu.id_                                                      AS approver_id,
       aiu.display_name_                                            AS approver_name,
       ahp.name_                                                    AS form_name,
       ahp.business_key_                                            AS business_key,
       ahp.proc_inst_id_                                            AS process_instance_id,
       aht.proc_def_id_                                             AS process_definition_id,
       arp.name_                                                    AS process_definition_name,
       ahp.tenant_id_                                               AS tenant_id,
       aht.assignee_                                                AS assignee,
       (CASE WHEN ahp.end_act_id_ IS NULL THEN FALSE ELSE TRUE END) AS is_finished,
       aht.start_time_                                              AS start_time,
       aht.end_time_                                                AS end_time,
       aht.duration_                                                AS duration
FROM act_hi_taskinst aht
         LEFT JOIN act_id_user aiu ON aht.assignee_ = aiu.id_
         LEFT JOIN act_hi_procinst ahp ON aht.proc_inst_id_ = ahp.proc_inst_id_
         LEFT JOIN act_re_procdef arp ON aht.proc_def_id_ = arp.id_
WHERE aht.end_time_ IS NOT NULL;

-----------------------我发起的流程-------------------------
CREATE OR REPLACE VIEW "public"."act_task_launched_list" AS
SELECT ahp.id_                                                      AS process_instance_id,
       ahp.proc_def_id_                                             AS process_definition_id,
       arp.name_                                                    AS process_definition_name,
       ahp.name_                                                    AS form_name,
       ahp.tenant_id_                                               AS tenant_id,
       ahp.business_key_                                            AS business_key,
       (
           SELECT v.name_
           FROM (
                    SELECT aht.proc_inst_id_,
                           aht.name_,
                           ROW_NUMBER()
                           OVER ( PARTITION BY aht.proc_inst_id_ ORDER BY aht.last_updated_time_ DESC ) AS data_sequence
                    FROM act_hi_taskinst aht
                ) v
           WHERE v.data_sequence = 1
             AND v.proc_inst_id_ = ahp.proc_inst_id_
       )                                                            AS task_name,
       ahp.start_time_                                              AS start_time,
       ahp.end_time_                                                AS end_time,
       ahp.duration_                                                AS duration,
       (CASE WHEN ahp.end_act_id_ IS NULL THEN FALSE ELSE TRUE END) AS is_finished,
       aiu.id_                                                      AS initiator_id,
       aiu.display_name_                                            AS initiator_name
FROM act_hi_procinst ahp
         LEFT JOIN act_id_user aiu ON ahp.start_user_id_ = aiu.id_
         LEFT JOIN act_re_procdef arp ON ahp.proc_def_id_ = arp.id_;

---------------历史运行节点信息排序视图---------------------
CREATE OR REPLACE VIEW "public"."act_hi_ranking_activity" AS
SELECT aha
           .*,
       ROW_NUMBER() OVER ( PARTITION BY aha.proc_inst_id_ ORDER BY aha.end_time_ ASC ) AS ranking
FROM act_hi_actinst aha;

---------------运行时运行节点信息排序视图---------------------
CREATE OR REPLACE VIEW "public"."act_ru_ranking_activity" AS
SELECT ara
           .*,
       ROW_NUMBER() OVER ( PARTITION BY ara.proc_inst_id_ ORDER BY ara.end_time_ ASC ) AS ranking
FROM act_ru_actinst ara;