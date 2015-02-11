# CDI Talk

## Demo 1 - Injection of ISBNGenerator in a web app

#### Create a project

* Go to the CDI directory : `cd $CDI`
* Launch Forge : `$SOFTWARE_HOME/Forge/xxx/bin/forge`
* Create the project `cditalk`

	`project-new --named cditalk`

* Show the project on Intellij IDEA : `pom.xml` and empty Maven directory structure.

#### Create an entity

	jpa-new-entity --named Book ;
	jpa-new-field --named title --length 50 ;
	jpa-new-field --named isbn ;
	jpa-new-field --named price --type java.lang.Float ;
	jpa-new-field --named discount --type java.lang.Float ;

* Show the entity (generated id, version...), Hibernate dependency in the `pom.xml` and `persistence.xml`.

#### Generate JSF pages for the entity

`scaffold-generate --targets org.cditalk.model.Book`

* Show the JSF backing bean `BookBean` and the `webapp/book` directory with all the web pages.

#### In Intellij, deploy and run the app on JBoss

* Got to [http://localhost:8080/book]()
* *I've created a Java EE application in 2 minutes, and that's because I was talking*

#### Create the ISBN NumberGenerator
 
	java-new-interface --named NumberGenerator --targetPackage org.cditalk.util ;
	java-new-class --named IsbnGenerator --targetPackage org.cditalk.util ;

* Interface should `extends Serializable`
* Add the method `String generateNumber()` to the interface
* `IsbnGenerator` should implement it (`implements NumberGenerator`) and method should `return "13-" + Math.random();`

#### Inject IsbnGenerator in the BookBean

* Inject IsbnGenerator `@Inject private NumberGenerator numberGenerator;`
* Use it to generate a number an persist a book in `public String update()` add `this.book.setIsbn(numberGenerator.generateNumber());`

#### Redeploy and run the app

* Create a new book with the number, show it's `13` 

## Demo 2 - Qualifiers for ISBN and ISSN generators

#### Create the ISSN NumberGenerator
 
In IntelliJ Idea just duplicate the `IsbnGenerator` into `IssnGenerator` and return 8 instead of 13

* `IssnGenerator` should `implements NumberGenerator`  and method should `return "8-" + Math.random();`
* In Forge, go to the `IssnGenerator` class and do a `ls`

#### Redeploy the app and show it doesn't deploy

* Look for message `WELD-001409: Ambiguous dependencies`

#### Create and apply the qualifiers

	cdi-new-qualifier --named EightDigits --targetPackage org.cditalk.util 
	cdi-new-qualifier --named ThirteenDigits --targetPackage org.cditalk.util 

* Show the code of qualifiers
* `@ThirteenDigits` on `IsbnGenerator`
* `@EightDigits` on `IssnGenerator` 

#### Redeploy the app and show it doesn't deploy

* Look for message `WELD-001408: Unsatisfied dependencies`

#### Add the @EightDigits to BookBean

* On `BookBean` add `@EightDigits` to the injection point

#### Redeploy and run the app

* Create a new book with the number, show it's `8` 


## Demo 3 - Producing the Entity Manager and Logger

#### Produce and Inject the Entity Manager (FIELD)

* Show `@PersistenceContext` in `BookBean`
* Create the `ResourceProducer` class

	`java-new-class --named ResourceProducer --targetPackage org.cditalk.util`

* `ResourceProducer` should implement `Serializable` 
* In `ResourceProducer` copy the `@PersistenceContext` from `BookBean`  
* In `BookBean` inject the EntityManager with `@Inject`	  
* Deploy the app and check that there is a unsatisfied dependency error
* In `ResourceProducer` produce the `EntityManager` with `@Produces`
* Deploy the app and check it's working
	
#### Produce and Inject the Logger (METHOD)

* Create the `LoggerProducer` class

	`java-new-class --named LoggerProducer --targetPackage org.cditalk.util`

* `LoggerProducer` should implement `Serializable` 
* Add the method producer 

code 

	@Produces
	private Logger produceLogger(InjectionPoint ip) {
	  return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

* Inject the logger in the `BookBean` with `@Inject private Logger logger`
* Use it to log a message in `public String update()` such as `logger.info("Book Created");`
* Redeploy and run the app and show the log


## Demo 4 - Produce a discount and add it to the web page

#### Produce a discount

* Create the `DiscountProducer` class

	`java-new-class --named DiscountProducer --targetPackage org.cditalk.util`

* Add Float `rate` attribute initialized with `5.5F`
* Produce the `Float` and give it the `@Named` `discountRate`

code
 
	public class DiscountProducer {
		@Produces
		@Named("discountRate")
		private Float rate = 5.5F;
	} 

#### Add it to the web page

* In the `create.xhtml` page add a read only `#{discountRate}`

code

	<h:outputLabel for="bookBeanBookDiscountRate" value="Discount Rate:"/>
	<h:panelGroup>
	  <h:inputText id="bookBeanBookDiscountRate" value="#{discountRate}" readonly="true"/>
	  <h:message for="bookBeanBookDiscountRate" styleClass="error"/>
	</h:panelGroup>
	<h:outputText/>

#### Redeploy and run the app

## Demo 5 - Alternative discount and ISBN number

#### Create an Alternative MockGenerator

* Create a `MockGenerator` copying/pasting `IsbnGenerator` 
* Add it the `@Alternative`, `@ThirteenDigits` and `@EightDigits`
* Return a `mock` string

code
    
    @Alternative
    @ThirteenDigits
    @EightDigits
    public class MockGenerator implements NumberGenerator {
	
	   public String generateNumber() {
		 return "mock-" + Math.random();
	   }
	}

* Deploy the app, nothing has changed
* Enable the alternative, create a book, the `mock` string is displayed
	
#### Add an alternative Discount

* In the `DiscountProducer` produce a new Float with same `@Named` and `@Alternative`  

code

	@Produces
	@Named("discountRate")
	@Alternative
	private Float rateAlt = 12.5F;
	
#### Enable both alternatives

* In `beans.xml` enable both alternatives

code

	<alternatives>
	  <class>org.cditalk.util.DiscountProducer</class>
	  <class>org.cditalk.util.MockGenerator</class>
	</alternatives>

## Demo 6 - Events and observers

#### Fire an event when the book is created

* In `BookBean` inject the event of type `Book`

code

	@Inject
	private Event<Book> eventBook;

* After the `persist` fire the event `eventBook.fire(book)`

#### InventoryService observes the event

* Create the `InventoryService` 

code

	java-new-class --named InventoryService --targetPackage org.cditalk.service
	
* Inject a logger `@Inject private Logger logger`
* Observe the `Book`

code

	public class InventoryService {
	
	   @Inject
	   private Logger logger;
	
	   private void observeBooks (@Observes Book book) {
		  logger.severe("%%%%%%%%%%%%%%%%%%%%%");
		  logger.severe("Book recevied " + book.getTitle());
	   }
	}


