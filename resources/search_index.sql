begin
ctx_ddl.create_preference('name_and_blurb','MULTI_COLUMN_DATASTORE');
ctx_ddl.set_attribute('name_and_blurb', 'columns','name,blurb');
end;

CREATE INDEX idx_ks_projects ON ks_projects(name)
     INDEXTYPE IS CTXSYS.CONTEXT PARAMETERS
     ('DATASTORE name_and_blurb');
     
select p.* from dpan.ks_projects p where contains(name, 'france', 1) > 0 order by score(1) desc;

CREATE INDEX idx_ks_users ON asugumar.ks_users(name)
     INDEXTYPE IS CTXSYS.CONTEXT;
     
select u.* from asugumar.ks_users u where contains(name, 'austin france', 1) > 0 order by score(1) desc;
