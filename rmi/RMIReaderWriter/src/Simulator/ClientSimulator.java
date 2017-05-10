package Simulator;

import Application.Client;

public class ClientSimulator {

	public static void main(String[] args) {
		String[][] clientArgs = new String[21][2];
		
		clientArgs[0] = new String[] {"writer", "78"};
		clientArgs[1] = new String[] {"reader", ""};
		clientArgs[2] = new String[] {"writer", "45"};
		clientArgs[3] = new String[] {"writer", "62"};
		clientArgs[4] = new String[] {"writer", "47"};
		clientArgs[5] = new String[] {"reader", ""};
		clientArgs[6] = new String[] {"reader", ""};
		clientArgs[7] = new String[] {"writer", "89"};
		clientArgs[8] = new String[] {"reader", ""};
		clientArgs[9] = new String[] {"reader", ""};
		clientArgs[10] = new String[] {"reader", ""};
		clientArgs[11] = new String[] {"reader", ""};
		clientArgs[12] = new String[] {"writer", "88"};
		clientArgs[13] = new String[] {"writer", "4"};
		clientArgs[14] = new String[] {"reader", ""};
		clientArgs[15] = new String[] {"writer", "99"};
		clientArgs[16] = new String[] {"writer", "45"};
		clientArgs[17] = new String[] {"reader", ""};
		clientArgs[18] = new String[] {"reader", ""};
		clientArgs[19] = new String[] {"writer", "78"};
		clientArgs[20] = new String[] {"reader", ""};
		
		for(int i = 0; i < clientArgs.length; i++)
			Client.main(clientArgs[i]);
	}

}
