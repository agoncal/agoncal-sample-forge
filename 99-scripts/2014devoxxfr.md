# Show a quick Demo of Forge

#### Introducing Forge and Forge commands

Press TAB to show the commands. Type `ls`, `about` and `echo Hello Devoxx`. Say that I'll be writing a very quick Java EE application with JPA, Bean Validation, JSF and REST, and deploy it on JBoss.

#### Creating a project

`project-new --named quickdemo --finalName quickdemo`

Show the project on Intellij IDEA : `pom.xml` and empty Maven directory structure.

#### Creating and entity

`jpa-new-entity --named QuickEntity`
`jpa-new-field --named quickstring`

Show the entity (generated id, version...), Hibernate dependency in the `pom.xml` and `persistence.xml`. Insist on *it doesn't implement @XMLRootElement*.

#### Adding Bean Validation constraint to the entity

`constraint-add --onProperty quickstring --constraint NotNull`

Show `NotNull` on the attribute `quickstring` and the `validation.xml` file

#### Preparing to generate a JSF application

`scaffold-setup`

In the `webapp` directory, show the resources (Bootstrap, images, templates..), under `WEB-INF` show `beans.xml`, `faces-config` and `web.xml`

#### Generate JSF pages for the entity

`scaffold-generate --targets org.quickdemo.model.QuickEntity`

Show the JSF backing bean `QuickEntityBean` and the `webapp/quickentity` directory with all the web pages.

#### Generate a REST interface

`rest-generate-endpoints-from-entities --targets org.quickdemo.model.QuickEntity`

Show the `rest` package, the `RestApplication` class with the `/rest` path

#### Build the application

`build`

Say that Forge uses Maven to build the application

#### Deploy it on JBoss

`as-setup --server jbossas7`
`as-start`
`as-deploy`

#### Execute the app

[http://localhost:8080/quickdemo]
[http://localhost:8080/quickdemo/rest/quickentities]

Create entities in the JSF app, show the REST interface, updade and delete data on JSF, and show REST again.

*I've created a Java EE application in 5 minutes, and that's because I was talking*

#### Undeploy the app and stop JBoss

`as-undeploy`
`as-shutdown`

cd ~~

# What happened behond our back ?

#### Forge generated Java code, HTML, JSF, XML, added stuff to the pom.xml

#### Show Forge slide

#### Show Roaster (let's generate some Java code with an API)

* On `agoncal-sample-forge`, go to `01-Roaster`
* Show how to create a Java Method with `JavaMethod.java`
* Show how to create a Java Annotation with `BeanValidationAnnotation.java`

#### Templating (let's generate more complex code with Templates)

* On `agoncal-sample-forge`, go to `03-Template`
* Say we use a Freemaker template and show `RestEndpoint.jv`. Show the variables `${entity}` or `${resourcePath}`
* Show the main class `RestEndpoint.java`
* And now let's add one method with Roaster

# Generate more complex apps about Books, conferences, speakers

Forge can be scripted.

#### And now let's write the same app with Angular

* On `99-Scripts` show the `ConferenceAngularApplication.fsh` script.
* On Forge type `run ConferenceAngularApplication.fsh`
* Import on Intellij Idea, show code, deploy on WildFly

#### Give it a try and contribute

* It's just a matter of Addons
* Create new addons
* Add new commands to existing addons
