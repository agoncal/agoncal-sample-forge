project-new --named demojsf ;


jpa-new-entity --named Speaker ;
jpa-new-field --named firstname ;
jpa-new-field --named surname ;
jpa-new-field --named bio --length 2000 ;
jpa-new-field --named twitter ;

constraint-add --onProperty firstname --constraint NotNull ;
constraint-add --onProperty surname --constraint NotNull ;
constraint-add --onProperty bio --constraint Size --max 2000


jpa-new-entity --named Talk ;
jpa-new-field --named title ;
jpa-new-field --named description --length 2000 ;
jpa-new-field --named room ;
jpa-new-field --named date --type java.util.Date --temporalType DATE ;
jpa-new-field --named speakers --type ~.model.Speaker --relationshipType One-to-Many ;

constraint-add --onProperty title --constraint NotNull ;
constraint-add --onProperty room --constraint NotNull ;
constraint-add --onProperty description --constraint Size --max 2000


java-new-enum --named Language --targetPackage ~.model ;
java-new-enum-const ENGLISH ;
java-new-enum-const FRENCH ;


jpa-new-entity --named Book ;
jpa-new-field --named isbn ;
jpa-new-field --named title ;
jpa-new-field --named author ;
jpa-new-field --named description --length 2000 ;
jpa-new-field --named price --type java.lang.Float ;
jpa-new-field --named nbOfPages --type java.lang.Integer ;
jpa-new-field --named publisher ;
jpa-new-field --named publicationDate --typeName java.util.Date --temporalType DATE ;
jpa-new-field --named language --type ~.model.Language ;
jpa-new-field --named imageURL ;
jpa-new-field --named pageURL ;


scaffold-setup ;
scaffold-generate --targets ~.model.Book ;
scaffold-generate --targets ~.model.Speaker ;
scaffold-generate --targets ~.model.Talk ;

rest-setup ;
rest-generate-endpoints-from-entities --targets ~..model.Book ;
rest-generate-endpoints-from-entities --targets ~..model.Speaker ;
rest-generate-endpoints-from-entities --targets ~..model.Talk ;
