// USING DEPTH FIRST SEARCH

// It can be run in any java ide



import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class State {
	private int HL; // husbandsLeft: husband count on the left
	private int WL; // wivesLeft: wives count on the left
	private int HR; // husbandsRight: husbands count on the right
	private int WR; // wivesRight: wives count on the right
	private boolean BL; // boatLeft: isBoat on the leftSide
	private ArrayList<String> testString;
	private String action; // action to create this state
	// constructor

	public State(int HL, int WL, boolean BL, String action) {
		this.HL = HL;
		this.WL = WL;
		this.BL = BL;
		this.HR = 3 - HL; // assuming we start on the left side (meaning that HR and WR are 0)
		this.WR = 3 - WL;
		this.action = action;
	}

	boolean equal(State S) {
		if (this.HL == S.getHL() && this.WL == S.getWL() && this.BL == S.isBL())
			return true;
		else
			return false;
	}

	boolean equal(int HL, int WL, boolean BL) {
		if (this.HL == HL && this.WL == WL && this.BL == BL)
			return true;
		else
			return false;
	}

	// getters
	public int getHL() {
		return HL;
	}

	public int getWL() {
		return WL;
	}

	public boolean isBL() {
		return BL;
	}

	public String getAction() {
		return action;
	}

	// setters
	public void setHL(int n) {
		this.HL = n;
	}

	public void setWL(int n) {
		this.WL = n;
	}

	public void setBL(boolean b) {
		this.BL = b;
	}

	public void setHR(int n) {
		this.HR = n;
	}

	public void setWR(int n) {
		this.WR = n;
	}

	/*
	 * toStringOut will print out the sequence of where the husbands and wives are
	 * and append them to a empty string and an arrayList of string. Will return an
	 * ArrayList This is in essence the result function
	 */
	ArrayList<String> toStringOut() {
		String s = "";
		ArrayList<String> nodeState = new ArrayList<>();

		// printing husbands left on the left side
		for (int i = 0; i < HL; i++) {
			s += "H";
			nodeState.add("H");
		}
		// printing wives on the left side
		for (int i = 0; i < WL; i++) {
			s += "W";
			nodeState.add("W");
		}
		// printing what side the boat is currently
		if (BL == true) { // left
			s += "BR";
			nodeState.add("BR");
		} else { // right
			s += "RB";
			nodeState.add("RB");
		}

		// prints husbands on the right
		for (int i = 0; i < HR; i++) {
			s += "H";
			nodeState.add("H");
		}
		// prints wives on the right
		for (int i = 0; i < WR; i++) {
			s += "W";
			nodeState.add("W");
		}
		return nodeState;
	}

	/*
	 * NOTE: this doubles as an action function expandedStates helps generate an
	 * arrayList of States that will help determine the different states that could
	 * exist given a specific scenario This will give us our successor states for
	 * the DFS algo The expanded states also help us in the process of generating
	 * actions as it gives us states that we want depending on the current count on
	 * either side
	 */
	ArrayList<State> expandStates() {
		ArrayList<State> expStates = new ArrayList<State>();

		// left side scenarios
		if (BL == true) { // if the boat is on the left side
			if (HL >= 2) { // if only 2 husbands on the left
				String s = "2Hs move from Left to Right      ";
				State temp = new State(HL - 2, WL, false, s); // generate a new state with only
				if (temp.isValid()) // if the amount of wives do not outnumber husbands, then we have a new state
					expStates.add(temp);
			}
			// 2 or more wives on the left
			if (WL >= 2) { // number of wives on left side
				String s = "2Ws move from Left to Right      ";
				State temp = new State(HL, WL - 2, false, s);
				if (temp.isValid())
					expStates.add(temp);
			}
			// one or more husbands on the left
			if (HL >= 1) {
				String s = "1H moves from Left to Right      ";
				State temp = new State(HL - 1, WL, false, s);
				if (temp.isValid())
					expStates.add(temp);
			}
			// one or more wives on the left side
			if (WL >= 1) {
				String s = "1W moves from Left to Right      ";
				State temp = new State(HL, WL - 1, false, s);
				if (temp.isValid())
					expStates.add(temp);
			}
			// one or more of husbands and wives
			if (WL >= 1 && HL >= 1) {
				String s = "1W and 1H move from Left to Right";
				State temp = new State(HL - 1, WL - 1, false, s);
				if (temp.isValid())
					expStates.add(temp);
			}
		} else {
			// right side scenarios

			// number of husbands is greater than 2 on right
			if (HR >= 2) {
				String s = "2Hs move from Right to Left      ";
				State temp = new State(HL + 2, WL, true, s);
				if (temp.isValid())
					expStates.add(temp);
			}

			// number of wives greater than 2 on right
			if (WR >= 2) {
				String s = "2Ws move from Right to Left      ";
				State temp = new State(HL, WL + 2, true, s);
				if (temp.isValid())
					expStates.add(temp);
			}
			// husbands on right greater than 1

			if (HR >= 1) {
				String s = "1H moves from Right to Left      ";
				State temp = new State(HL + 1, WL, true, s);
				if (temp.isValid())
					expStates.add(temp);
			}
			// wives greater than 1 on the right
			if (WR >= 1) {
				String s = "1W moves from Right to Left      ";
				State temp = new State(HL, WL + 1, true, s);
				if (temp.isValid())
					expStates.add(temp);
			}
			// scenario where both wive and husbands is greater than 1
			if (WR >= 1 && HR >= 1) {
				String s = "1W and 1H move from Right to Left";
				State temp = new State(HL + 1, WL + 1, true, s);
				if (temp.isValid())
					expStates.add(temp);
			}
		}

		return expStates;
	}

	// method makes sure that number of wives are not outnumbering husbands
	public boolean isValid() {
		if (HL > 0 && WL > HL) // wives outnumbering husbands on the left
			return false;
		if (HR > 0 && WR > HR) // wives outnumbering husbands on the right
			return false;
		return true; // return true otherwise
	}

	/*
	 * GOAL_TEST function will take a state and determine if the amount of husbands
	 * and wives on the left side is exactly 0 if so, we have our desired state
	 */
	public boolean GOAL_TEST(Node state) {
		if (state.getState().getHL() == 0 && state.getState().getWL() == 0) {
			return true;
		}
		return false;
	}
}

public class Node {
	/*
	 * State S contains the sequence that the search is in at any point parentNode
	 * will keep track of previous states that led to its existence action will hold
	 * what action to go with
	 */
	private State S;
	// Reference to parent node
	private Node parentNode;
	String actionN;

	// Constructor to init newNode with a state and its parent node
	Node(State S, Node parentNode, String action) {
		this.S = S;
		this.parentNode = parentNode;
		// ACTION
		this.actionN = action;
	}

	// getters
	State getState() {
		return S;
	}

	Node getParentNode() {
		return parentNode;
	}

	String getActionN() {
		return actionN;
	}

	/*
	 * frontier is a stack becuase we are using DFS (LIFO) frontier will keep track
	 * of nodes that are expanded upon explored will help make sure that we do not
	 * end up in loopy paths because it will keep track of already visited nodes
	 */
	static ArrayList<Node> frontier = new ArrayList<Node>();
	static ArrayList<State> explored = new ArrayList<State>();

	// method that determines if a state is within explored
	// returns true means we won't explore the node again but if false,
	// we go through with it again.
	static boolean isExplored(State S) {
		for (int i = 0; i < explored.size(); i++) {
			if (S.equal(explored.get(i)))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		State S1 = new State(3, 3, true, "Start"); // initial state
		Node N1 = new Node(S1, null, "Start                            "); // adding the first node to explored (which
																			// has init state)
		explored.add(S1);
		frontier.add(N1);

		// START OF DFS ALGO
		Node FN = null; // FN will keep track of the parent node that led to a solution
		while (frontier.size() > 0) {
			// get node from frontier and then pop it from the list
			Node parentN = frontier.get(frontier.size() - 1);
			frontier.remove(frontier.size() - 1);

			// if no more husbands and wives exist on the left side, then solution is found
			// GOAL-TEST
			if (parentN.getState().GOAL_TEST(parentN)) { // goal test
				System.out.println("WE FOUND A SOLUTION");
				FN = parentN;
				break;
			}

			// expanded states for the parentN node saved in the states arrayList
			// ArrayList<State> States = parentN.getState().expandStates();
			ArrayList<State> States = SUCCESSOR(parentN);

			/*
			 * loop through the states arrayList and if a state was not explored, we then
			 * want to add that to the explored array and also our frontier so that we can
			 * observe the other potential nodes
			 */
			for (int i = 0; i < States.size(); i++) {
				if (!isExplored(States.get(i))) {
					explored.add(States.get(i));
					// CHILD-NODEs which are valid and added to frontier
					CHILD_NODE(States.get(i), parentN, States.get(i).getAction());

				}
			}
		}
		// if a path was not found and the initial value of FN
		// stays the same
		if (FN == null)
			System.out.println("WE FOUND NO SOLUTION");
		/*
		 * printing out the sequence that leads us to our solution ArrayList of nodes
		 * will hold the sequence of states will backtrace through the parent nodes to
		 * recreate the path nodes will contain the sequence but backwards so another
		 * for loop will print out the ArrayList of nodes starting from the beginning to
		 * the end
		 */
		else {

			int input;
			String continueOn;
			Scanner scan = new Scanner((System.in));
			System.out.println("Do you want to see the legend for the encoding scheme?");
			System.out.println("Enter 1 for yes 2 for no: ");
			input = scan.nextInt();
			scan.nextLine();

			if (input == 1) {
				legend();
				System.out.println("Press any key to continue .....");
				continueOn = scan.nextLine();
			} else {
				System.out.println("Printing out the sequence");
				System.out.println("Press any key to continue .....");
				continueOn = scan.nextLine();
			}
			scan.close();

			ArrayList<ArrayList> nodes = new ArrayList<>();

			// ACTIONS: A serie of actions to solve the problem
			ArrayList<String> ACTIONS = new ArrayList<String>();

			SOLUTION(nodes, ACTIONS, FN);

			System.out.println("Using the DFS Algorithm, we found this: ");
			System.out.println("Initial State: ");
			// SOLUTION
			// A set of action
			for (int i = nodes.size() - 1; i >= 0; i--) {
				System.out.println("Action: " + ACTIONS.get(i) + " -> " + nodes.get(i) + " ->");
			}
			System.out.println("Final State");
		}

	}

	// CHILD-NODE Function, to explore and add nodes to frontier.
	static void CHILD_NODE(State S, Node P, String actionN) {
		frontier.add(new Node(S, P, actionN));
	}

	// SUCCESSOR Function: return all possible states actions apply to a node
	static ArrayList<State> SUCCESSOR(Node P) {
		return P.getState().expandStates();
	}

	// SOLUTON Function: construct solution of the problem
	static void SOLUTION(ArrayList<ArrayList> nodes, ArrayList<String> ACTIONS, Node FN) {
		while (FN != null) {
			nodes.add(FN.getState().toStringOut());
			ACTIONS.add(FN.getActionN());
			FN = FN.getParentNode();
		}
	}

	public static void legend() {
		System.out.println("");
		System.out.println("-------------------");
		System.out.println("H = HUSBAND");
		System.out.println("W = WIFE");
		System.out.println("B = BOAT");
		System.out.println("R = RIVER");
		System.out.println("-------------------");
		System.out.println("");
	}
}

// OUTPUT

/*
 * WE FOUND A SOLUTION Do you want to see the legend for the encoding scheme?
 * Enter 1 for yes 2 for no: 1
 * 
 * ------------------- H = HUSBAND W = WIFE B = BOAT R = RIVER
 * -------------------
 * 
 * Press any key to continue .....
 * 
 * Using the DFS Algorithm, we found this: Initial State: Action: Start -> [H,
 * H, H, W, W, W, BR] -> Action: 1W and 1H move from Left to Right -> [H, H, W,
 * W, RB, H, W] -> Action: 1H moves from Right to Left -> [H, H, H, W, W, BR, W]
 * -> Action: 2Ws move from Left to Right -> [H, H, H, RB, W, W, W] -> Action:
 * 1W moves from Right to Left -> [H, H, H, W, BR, W, W] -> Action: 2Hs move
 * from Left to Right -> [H, W, RB, H, H, W, W] -> Action: 1W and 1H move from
 * Right to Left -> [H, H, W, W, BR, H, W] -> Action: 2Hs move from Left to
 * Right -> [W, W, RB, H, H, H, W] -> Action: 1W moves from Right to Left -> [W,
 * W, W, BR, H, H, H] -> Action: 2Ws move from Left to Right -> [W, RB, H, H, H,
 * W, W] -> Action: 1W moves from Right to Left -> [W, W, BR, H, H, H, W] ->
 * Action: 2Ws move from Left to Right -> [RB, H, H, H, W, W, W] -> Final State
 * 
 */