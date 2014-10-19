echo Hi, this is a Hands on Lab about JBoss Forge

project-new --named quickdemo --finalName quickdemo

jpa-new-entity --named QuickEntity
jpa-new-field --named quickstring

scaffold-setup
scaffold-generate --targets org.quickdemo.model.QuickEntity

build

as-setup --server jbossas7
as-start
as-deploy

http://localhost:8080/quickdemo

as-undeploy

rest-generate-endpoints-from-entities --targets org.quickdemo.model.QuickEntity

build

as-deploy

http://localhost:8080/quickdemo/rest/quickentities

as-undeploy
as-shutdown

cd ~~


