import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

class jobs implements Comparable<jobs> {
	String name;
	int arr;
	int ttd;
	int ori;

	public jobs(String name, String arr, String ttd, int ori) {
		this.arr = Integer.parseInt(arr);
		this.ttd = Integer.parseInt(ttd);
		this.name = name;
		this.ori = ori;
	}

	public void sarr(String arr) {
		this.arr = Integer.parseInt(arr);
	}

	public void sttd(String ttd) {
		this.ttd = Integer.parseInt(ttd);
	}

	public void sittd(int ttd) {
		this.ttd = ttd;
	}

	public void sname(String name) {
		this.name = name;
	}

	public void sori(int ori) {
		this.ori = ori;
	}

	public int gttd() {
		return this.ttd;
	}

	public int garr() {
		return this.arr;
	}

	public int sori() {
		return this.ori;
	}

	@Override
	public int compareTo(jobs arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}

public class Schedule {

	// building a array of job units with lotsnumber of recieved data
	public static void main(String[] args) {
		String jobfile = null;
		int numberofjob = 0;
		if (args[0] == null) {
			System.out.println("No File name recieved, System End");
			System.exit(1);
		}
		jobfile = args[0];

		List<String> joblist = new ArrayList<String>();
		// copying job list into a list
		try {
			BufferedReader readfile = new BufferedReader(new FileReader(jobfile));
			String job;
			while ((job = readfile.readLine()) != null) {
				joblist.add(job);
			}
			readfile.close();
		} catch (Exception e) {
			System.out.println("Can not open file, System end");
		}
		// get all the schedulers
		numberofjob = joblist.size();
		List<ArrayList<String>> listedjob = new ArrayList<ArrayList<String>>();
		jobs[] listedjobs = new jobs[joblist.size()];
		jobs[] listedjobs1 = new jobs[joblist.size()];
		jobs[] listedjobs2 = new jobs[joblist.size()];
		jobs[] listedjobs3 = new jobs[joblist.size()];
		jobs[] listedjobs4 = new jobs[joblist.size()];
		jobs[] listedjobs5 = new jobs[joblist.size()];
		for (int i = 0; i < numberofjob; i++) {
			Scanner temp = new Scanner(joblist.get(i));
			temp.useDelimiter("\t");
			ArrayList<String> templist = new ArrayList<String>();
			String name = temp.next();
			String tta = temp.next();
			String ttd = temp.next();
			templist.add(name);
			templist.add(tta);
			templist.add(ttd);
			listedjob.add(templist);
			listedjobs[i] = new jobs(name, tta, ttd, i);
			listedjobs1[i] = new jobs(name, tta, ttd, i);
			listedjobs2[i] = new jobs(name, tta, ttd, i);
			listedjobs3[i] = new jobs(name, tta, ttd, i);
			listedjobs4[i] = new jobs(name, tta, ttd, i);
			listedjobs5[i] = new jobs(name, tta, ttd, i);
		}
		// big switch for all the functions
		switch (args[1]) {
		case "FCFS":
			// finished
			FCFS(listedjob);
			break;
		case "RR":
			// finished
			RR(listedjobs);
			break;
		case "SPN":
			// finished
			SPN(listedjobs);
			break;
		case "SRT":
			SRT(listedjobs);
			break;
		case "HRRN":
			// finished
			HRRN(listedjobs);
			break;
		case "FB":
			FB(listedjobs);
			break;
		case "ALL":
			FCFS(listedjob);
			RR(listedjobs1);
			SPN(listedjobs2);
			SRT(listedjobs3);
			HRRN(listedjobs4);
			FB(listedjobs5);
			break;
		}
	}

	public static void RR(jobs[] listedjobs) {
		System.out.println("RR");
		int tttd = 0;
		for (int j = 0; j < listedjobs.length; j++) {
			System.out.print(listedjobs[j].name);
			tttd = tttd + listedjobs[j].ttd;
			System.out.print(" ");
		}
		System.out.println();
		int timer = 0;
		ArrayList<Boolean> added = new ArrayList<Boolean>();
		LinkedList<jobs> queue = new LinkedList<jobs>();
		for (int i = 0; i < listedjobs.length; i++) {
			added.add(false);
		}
		while (timer < tttd) {
			timer++;
			for (int j = 0; j < listedjobs.length; j++) {
				if (timer >= listedjobs[j].arr && added.get(listedjobs[j].ori) == false) {
					queue.addLast(listedjobs[j]);
					added.set(j, true);
				} else if (timer < listedjobs[j].arr && added.get(listedjobs[j].ori) != false) {
					System.out.println("NULL, No Process");
				}
			}
			jobs cur = queue.getFirst();
			queue.removeFirst();
			for (int i = 0; i < cur.ori; i++) {
				System.out.print("  ");
			}
			System.out.print("X");
			System.out.println();
			cur.ttd--;
			if (cur.ttd > 0) {
				queue.addLast(cur);
			}
		}
	}

	// Shortest Process Next
	// expected theres always at least one process waiting
	public static void SPN(jobs[] listedjobs) {
		System.out.println("SPN");
		int tttd = 0;
		for (int j = 0; j < listedjobs.length; j++) {
			System.out.print(listedjobs[j].name);
			tttd = tttd + listedjobs[j].ttd;
			System.out.print(" ");
		}
		System.out.println();

		int timer = 0;
		ArrayList<Boolean> done = new ArrayList<Boolean>();
		PriorityQueue<Integer> que = new PriorityQueue<Integer>();
		for (int i = 0; i < listedjobs.length; i++) {
			done.add(false);
		}
		while (timer < tttd) {
			for (int j = 0; j < listedjobs.length; j++) {
				if (timer >= listedjobs[j].arr && done.get(listedjobs[j].ori) == false) {
					que.add(listedjobs[j].ttd);
					done.set(j, true);
				} else if (timer < listedjobs[j].arr && done.get(listedjobs[j].ori) != false) {
					System.out.println("NULL, No Process");
				}
			}
			int cur = que.poll();
			for (int i = 0; i < listedjobs.length; i++) {
				if (cur == listedjobs[i].ttd) {
					for (int b = 0; b < cur; b++) {
						for (int a = 0; a < i; a++) {
							System.out.print("  ");
						}
						timer++;
						System.out.print("X");
						System.out.println("");
					}
				}
			}
		}
	}

	public static void SRT(jobs[] listedjobs) {
		System.out.println("SRT");
		int tttd = 0;
		for (int j = 0; j < listedjobs.length; j++) {
			System.out.print(listedjobs[j].name);
			tttd = tttd + listedjobs[j].ttd;
			System.out.print(" ");
		}
		System.out.println();
		int timer = 0;
		ArrayList<Boolean> added = new ArrayList<Boolean>();
		LinkedList<jobs> queue = new LinkedList<jobs>();
		for (int i = 0; i < listedjobs.length; i++) {
			added.add(false);
		}
		while (timer < tttd) {
			for (int j = 0; j < listedjobs.length; j++) {
				if (timer >= listedjobs[j].arr && added.get(listedjobs[j].ori) == false) {
					queue.addFirst(listedjobs[j]);
					added.set(j, true);
				} else if (timer < listedjobs[j].arr && added.get(listedjobs[j].ori) != false) {
					System.out.println("NULL, No Process");
				}
			}
			// get the least of process
			jobs cur = queue.getFirst();
			queue.removeFirst();
			for (int i = 0; i < queue.size(); i++) {
				jobs temp = queue.getFirst();
				queue.removeFirst();
				if (temp.ttd > cur.ttd) {
					queue.addLast(temp);
				} else {
					queue.addLast(cur);
					cur = temp;
				}
			}
			// print
			for (int i = 0; i < cur.ori; i++) {
				System.out.print("  ");
			}
			System.out.println("X");
			cur.ttd--;
			if (cur.ttd > 0) {
				queue.addFirst(cur);
			}
			timer++;
		}

	}

	public static void HRRN(jobs[] listedjobs) {
		System.out.println("HRRN");
		int tttd = 0;
		for (int j = 0; j < listedjobs.length; j++) {
			System.out.print(listedjobs[j].name);
			tttd = tttd + listedjobs[j].ttd;
			System.out.print(" ");
		}
		System.out.println();

		int timer = 0;
		ArrayList<Boolean> added = new ArrayList<Boolean>();
		LinkedList<jobs> queue = new LinkedList<jobs>();
		for (int i = 0; i < listedjobs.length; i++) {
			added.add(false);
		}
		while (timer < tttd) {
			// check for new arrival
			for (int j = 0; j < listedjobs.length; j++) {
				if (timer >= listedjobs[j].arr && added.get(listedjobs[j].ori) == false) {
					queue.add(listedjobs[j]);
					added.set(j, true);
				} else if (timer < listedjobs[j].arr && added.get(listedjobs[j].ori) != false) {
					System.out.println("NULL, No Process");
				}
			}
			jobs cur = queue.getFirst();
			int temp = 0;
			temp = timer;
			temp = temp - queue.getFirst().arr;
			temp = temp + queue.getFirst().ttd;
			temp = temp / queue.getFirst().ttd;
			queue.removeFirst();
			for (int i = 0; i < queue.size(); i++) {
				int newtemp = timer;
				newtemp = newtemp - queue.getFirst().arr;
				newtemp = newtemp + queue.getFirst().ttd;
				newtemp = newtemp / queue.getFirst().ttd;
				if (newtemp > temp) {
					queue.add(cur);
					cur = queue.getFirst();
					queue.removeFirst();
				}
			}
			for (int i = 0; i < cur.ttd; i++) {
				for (int o = 0; o < cur.ori; o++) {
					System.out.print("  ");
				}
				timer++;
				System.out.println("X");
			}
		}
	}

	public static void FB(jobs[] listedjobs) {
		System.out.println("FB");
		int tttd = 0;
		for (int j = 0; j < listedjobs.length; j++) {
			System.out.print(listedjobs[j].name);
			tttd = tttd + listedjobs[j].ttd;
			System.out.print(" ");
		}
		System.out.println();

		int timer = 0;
		ArrayList<Boolean> added = new ArrayList<Boolean>();
		LinkedList<jobs> queue1 = new LinkedList<jobs>();
		LinkedList<jobs> queue2 = new LinkedList<jobs>();
		LinkedList<jobs> queue3 = new LinkedList<jobs>();
		for (int i = 0; i < listedjobs.length; i++) {
			added.add(false);
		}
		while (timer < tttd) {
			// check for new arrival
			for (int j = 0; j < listedjobs.length; j++) {
				if (timer >= listedjobs[j].arr && added.get(listedjobs[j].ori) == false) {
					queue1.addLast(listedjobs[j]);
					added.set(j, true);
				} else if (timer < listedjobs[j].arr && added.get(listedjobs[j].ori) != false) {
					System.out.println("NULL, No Process");
				}
			}
			timer++;
			jobs cur;
			if (!queue1.isEmpty()) {
				cur = queue1.getFirst();
				queue1.removeFirst();
				for (int o = 0; o < cur.ori; o++) {
					System.out.print("  ");
				}
				System.out.println("X");
				cur.ttd--;
				for (int j = 0; j < listedjobs.length; j++) {
					if (timer >= listedjobs[j].arr && added.get(listedjobs[j].ori) == false) {
						queue1.addLast(listedjobs[j]);
						added.set(j, true);
					} else if (timer < listedjobs[j].arr && added.get(listedjobs[j].ori) != false) {
						System.out.println("NULL, No Process");
					}
				}
				if (cur.ttd > 0) {
					if (queue1.isEmpty() && queue2.isEmpty() && queue3.isEmpty()) {
						queue1.addLast(cur);
					} else {
						queue2.addLast(cur);
					}
				}
			} else if (!queue2.isEmpty()) {
				cur = queue2.getFirst();
				queue2.removeFirst();
				for (int o = 0; o < cur.ori; o++) {
					System.out.print("  ");
				}
				System.out.println("X");
				cur.ttd--;
				if (cur.ttd > 0) {
					queue3.addLast(cur);
				}
			} else {
				cur = queue3.getFirst();
				queue3.removeFirst();
				for (int o = 0; o < cur.ori; o++) {
					System.out.print("  ");
				}
				System.out.println("X");
				cur.ttd--;
				if (cur.ttd > 0) {
					queue3.addLast(cur);
				}
			}
			for (int j = 0; j < listedjobs.length; j++) {
				if (timer >= listedjobs[j].arr && added.get(listedjobs[j].ori) == false) {
					queue1.addLast(listedjobs[j]);
					added.set(j, true);
				} else if (timer < listedjobs[j].arr && added.get(listedjobs[j].ori) != false) {
					System.out.println("NULL, No Process");
				}
			}
		}
	}

	// First Come First Serve
	public static void FCFS(List<ArrayList<String>> listedjob) {
		System.out.println("FCFS");
		for (int j = 0; j < listedjob.size(); j++) {
			System.out.print(listedjob.get(j).get(0));
			System.out.print(" ");
		}
		System.out.println();
		for (int i = 0; i < listedjob.size(); i++) {
			int ttd = Integer.parseInt(listedjob.get(i).get(2));
			for (int k = 0; k < ttd; k++) {
				for (int j = 0; j < i; j++) {
					System.out.print("  ");
				}
				System.out.println("X");
			}
		}
	}
}
