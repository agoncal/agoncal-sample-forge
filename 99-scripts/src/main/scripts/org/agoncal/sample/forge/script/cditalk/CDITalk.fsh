#  #####################  #
#  Creates a new project  #
#  #####################  #

project-new --named cditalk ;



#  ########################  #
#  Creates the domain model  #
#  ########################  #

#  Book entity
#  ############
jpa-new-entity --named Book ;
jpa-new-field --named title --length 50 ;
jpa-new-field --named isbn ;
jpa-new-field --named price --type java.lang.Float ;
jpa-new-field --named discount --type java.lang.Float ;



#  #####################  #
#  Adding a Service Tier  #
#  #####################  #

java-new-class --named InventoryService --targetPackage org.cditalk.service ;



#  #######################  #
#  Creates utility classes  #
#  #######################  #

#  Logging Producer
#  ############
java-new-bean --named LoggingProducer --targetPackage org.cditalk.util ;

#  Number generators interface and qualifier
#  ############
java-new-interface --named NumberGenerator --targetPackage org.cditalk.util ;
java-new-class --named IsbnGenerator --targetPackage org.cditalk.util ;
java-new-class --named IssnGenerator --targetPackage org.cditalk.util ;
java-new-class --named MockGenerator --targetPackage org.cditalk.util ;

cdi-new-qualifier --named EightDigits --targetPackage org.cditalk.util ;
cdi-new-qualifier --named ThirteenDigits --targetPackage org.cditalk.util ;

#  Producer
#  ############
java-new-class --named ResourceProducer --targetPackage org.cditalk.util ;
java-new-class --named LoggerProducer --targetPackage org.cditalk.util ;
java-new-class --named DiscountProducer --targetPackage org.cditalk.util ;



#  #############################  #
#  Generates JSF beans and pages  #
#  #############################  #

scaffold-generate --targets org.cditalk.model.Book ;

