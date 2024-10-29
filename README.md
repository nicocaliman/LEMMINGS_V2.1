#LEMMINGS_V2.1
The command pattern
In the application built in the previous assignment, the user could enter several different commands in order to update the game, reset the game, ask for help, etc. The objective of the first part of the refactoring is to introduce a structure to the part of the code that is concerned with processing the user commands which will facilitate the addition of new commands, i.e. which will enable new commands to be added with minimal modifications to the existing code. This structure is the well-known software design pattern 2 referred to as the command pattern 3. The general idea is to encapsulate each user action in its own class where, in this case, the user actions are the commands.

The following classes are involved in our application of the command pattern:

Commmand: an abstract class that encapsulates the functionality that is common to all the concrete commands. It has four attributes of type String, initialised in the constructor: name, shortcut, details, help, and getter methods for each of these attributes.

NoParamsCommand: an abstract class that inherits from Commmand and which is the base class of any command class representing a command with no parameters, the case for all of the commands we have defined so far.

Concrete command classes: HelpCommand, ExitCommand, etc., one class for each program command. In the case of a command with parameters, the corresponding class will have attributes to store the value of these parameters. Each concrete command class has (at least) the following three methods:

protected boolean matchCommand(String): checks if the first word of the text introduced by the user via the keyboard (minimally processed) corresponds to the command name. It is called by the parse method.

public Command parse(String[]): checks if the text introduced by the user via the keyboard (minimally processed) corresponds to a use of the command (which involves checking the validity of the parameter values):

If so, it returns an instance of the owning class, which then represents this use of the command. In the case of a command with no parameters, the parse method can simply return the value this. but in the case of a command with parameters, to avoid creating fragile code 4, it must return a new instance of the command class, rather than changing the values of the attributes of the containing object and then returning this.

If not, it returns the value null.

public void execute(Game, GameView): executes the functionality associated to the command by calling a method of the Game class, updating the view where necessary (in some commands, this method may also perform some other actions). In later versions of the program, command execution may fail, in which case updating the view will consist of printing an error message instead of printing the current state of the game.

Controller: the code of the controller class is now reduced to only a few lines since most of the functionality that it included in Assignment 1 is now delegated to the concrete command classes.

Main loop of the program. In the previous assignment, in order to know which command to execute, the main loop of the program in the run method of the controller contained a switch or if-else ladder with one option for each of the commands. In the reduced version of the controller, the run method has the following aspect (your code does not have be be exactly the same but should be similar):

while (!game.isFinished()) {

    String[] userWords = view.prompt();
    Command command = CommandGenerator.parse(userWords);

    if (command != null) 
        command.execute(game, view);
    else 
        view.showError(Messages.UNKNOWN_COMMAND);
}   
Basically, while the game is not finished (due to internal game reasons or to a user exit), the program reads the text entered by the user, parses it to obtain an object of class Command and then calls the execute method of this object to effect the functionality of the command entered by the user and to update the view after doing so, if necessary. In the case where the input text does not correspond to any of the existing commands, the error message Messages.UNKNOWN_COMMAND is printed.

Genericity of the Controller class. The most important part of the code for the main loop shown above is the following line:

Command command = CommandGenerator.parse(userWords);
The key point is that the controller is generic: it only handles abstract commands and does not know which concrete command is being executed; the knowledge of what functionality corresponds to each command is contained in each concrete command class. It is this mechanism that facilitates the addition of new concrete commands with minimal changes to the existing code.

The CommandGenerator class. The parse method of this class is a static method which returns an instance of the concrete command class that corresponds to the text entered by the user. To this end, the CommandGenerator class has a static attribute containing a list of instances of the class Command, concretely, it contains exactly one instance of each of the existing command classes. The parse method of the CommandGenerator method traverses this list calling the parse method of each of its command class instances. If any of these parse methods returns a non-null value (which will be an instance of one of the command classes), the parse of the CommandGenerator returns this non-null value, otherwise it returns the value null.

The CommandGenerator contains another static method commandHelp that generates the output of the help command (so must be called by the execute method of the HelpCommand class). Like the parse command, it accomplishes its task by traversing the AVAILABLE_COMMANDS list but in this case invoking the helpText() method of each object in the list.

The following is a skeleton of this code:

public class CommandGenerator {

    private static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(
        new UpdateCommand(),
        new ResetCommand(),
        new HelpCommand(),
        new ExitCommand(),
        // ...
    );

    public static Command parse(String[] commandWords){
	//...
    }

    public static String commandHelp(){
	//...
    }

}
The concrete command classes As already stated, each command has four attributes of type String: its name, its abbreviated name (or shortcut), the initial part of its help message, and the details of its help message. For example, the concrete command HelpCommand has the following constants and its constructor passes their values to the constructor of its superclass as follows:

public class HelpCommand extends NoParamsCommand {

	private static final String NAME = Messages.COMMAND_HELP_NAME;
	private static final String SHORTCUT = Messages.COMMAND_HELP_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_HELP_DETAILS;
	private static final String HELP = Messages.COMMAND_HELP_HELP;

	public HelpCommand(){
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

    // Implementation of execute
}
The NoParamsCommand class which inherits from Command is the superclass of all concrete command classes that represent commands with no parameters such as HelpCommand. The raison d'être of this class is that the parsing of all commands with no parameters only differs in the value of the name and shortcut attributes used to compare with the input text, so that the parse method of all these classes can be inherited from the NoParamsCommand class. Evidently, the same is not true of the execute method which will be different for every command, for which reason, the NoParamsCommand class must be abstract.

Inheritance and polymorphism
We have seen that the use of inheritance in the Command inheritance hierarchy significantly reduces the repetition of code. Moreover, the use of inheritance and polymorphism in the command pattern greatly facilitates the introduction of new commands, the key aspect being that the Controller class is generic, i.e. it does not handle specific commands but only handles objects of the abstract class Command.

Similarly, use of inheritance in an inheritance hierarchy of game objects would also reduce the repetition of code and facilitate the introduction of new game objects. The key aspect to obtaining the latter benefit is that the Game class be generic, i.e. it should not handle specific game objects only objects of an abstract class called GameObject, from which all the concrete game object classes (currently Lemming, Wall and ExitDoor), derive. So the game code must not seek to identify the dynamic type (i.e. which concrete subclass of GameObject) of the objects it is handling. This abstract class should contain all the attributes and methods that are common to all the concrete game object classes; where appropriate, each concrete game object class can overwrite inherited methods to implement its own behaviour. Note that

whether to define an abstract method in GameObject, or a method containing default behaviour, is a design decision,

in accord with the DRY principle, attributes and non-abstract methods should always be placed in the highest class possible of the inheritance hierarchy.

All game objects have, at least, an attribute to store the game, an attribute to store their position and a boolean attribute to indicate whether they are alive or not. They have, at least, methods to manipulate their position and a method to communicate whether they are alive or not. They will also have the method:

public void update()

which, in the case of the Wall class, has an empty body.

The game object container
Having refactored the code for the commands and for the game objects, we now turn our attention to the management of the game objects. As in the previous assignment, the game objects will be managed by the GameObjectContainer class. However, instead of using multiple lists, we can take advantage of the inheritance hierarchy of game objects to store them all on a single list of objects of type GameObject. For simplicity, we use an ArrayList 5 of elements of type GameObject:

public class GameObjectContainer {

	private List<GameObject> gameObjects;

	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
	}
    //...
}
Observe that, like the Game class, the GameObjectContainer class only deals with objects of the abstract class GameObject so, like the game code, the container code must not seek to identify the dynamic type (i.e. which concrete subclass of GameObject) of the objects it is handling. Finally, it is of great importance that the implementation details of the GameObjectContainer be private so, for example, it should not export the value of any of the attributes of the ArrayList class that it is using to store the game objects. This information hiding enables the implementation of the container to be changed without affecting the rest of the program code.

Interfaces implemented by the Game class
The Game class offers services to different parts of the program, namely:

Controller: invokes those methods of the game, such as update or reset, that implement the commands entered by the user; after the above-described refactoring, the calls to these methods are made from the body of the execute methods of the Command classes. It also invokes methods that return information about the state of the game, such as isFinished.

View: invokes methods that return information about the state of the game, such as getCycle, numLemmingsInBoard or positionToString, that is needed to display the current state.

Model: the game objects (part of the model, as is the Game class itself) invoke those methods of the game, such as isInAir or lemmingArrived, that concern interactions between game objects. Since these calls to the game result from calls by the game via the container, usually as part of an update, they are referred to as callbacks.

Notice that with the current implementation, nothing prevents the model invoking a method of Game that was designed for the controller to invoke, e.g. a game object invoking the reset method, or the view invoking a method of Game designed for the model to invoke, e.g. the game view invoking the lemmingArrived method, etc. In order for the compiler to detect such inconsistent invocations we can use interfaces to define partial views on the services offered by the Game class. To that end, we define the following three interfaces:

GameModel to represent the controller's view of the services offered by the Game class,

GameStatus to represent the view's view of the services offered by the Game class,

GameWorld to represent the model's view of the services offered by the Game class.

For example:

public interface GameModel {

	public boolean isFinished();
	public void update();
	public void reset();
	// ...
}
The Game class must then implement these interfaces:

public class Game implements GameModel, GameStatus, GameWorld {

	// ... 
	private GameObjectContainer container;
	private int nLevel;
	// ...
	
	// Methods declared in GameModel
	// ...
	// Methods declared in GameWorld
	// ...
	// Methods declared in GameStatus
	// ...
	// Other methods
	// ...
}
Finally, in each part of the three parts of the program, we must replace each occurrence of the type Game by the corresponding interface type. For example, the execute method of the Command class now has the following form:

public abstract void execute(GameModel game, GameView view);
