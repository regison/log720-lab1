import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class ServerPoste {
	private org.omg.CORBA.ORB orb;
	private NamingContextExt nc;
	
	public ServerPoste(String[] args) throws InvalidName{
		
		this.orb = org.omg.CORBA.ORB.init(args, null);
		// get hold of the naming service
		this.nc = NamingContextExtHelper.narrow(orb
				.resolve_initial_references("NameService"));
	}
	public static void main(String [] args){
		
	}
}
