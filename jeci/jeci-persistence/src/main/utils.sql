
-- statistics on number of designation per value
-- first column gives the number of designations, second column the number of values that have this number of designations
select c2.c1, count(*) from (select count(*) c1 from systdesignation group by syd_syv_id) c2 group by c2.c1;

-- number of taxons with null parent grouped by taxon rank
select syv_crf_id, count(*) from systvalue where syv_syv_id is null and SYV_SYV_ID_SYNONYMFOR is null group by syv_crf_id;

