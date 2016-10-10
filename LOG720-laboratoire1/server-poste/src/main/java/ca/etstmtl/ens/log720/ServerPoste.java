import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;

public class ServerPoste {
	private org.omg.CORBA.ORB orb;
	private org.omg.PortableServer.POA poa;
	private org.omg.PortableServer.Servant servant_dos;
	private org.omg.PortableServer.Servant servant_inf;
	private org.omg.CORBA.Object obj_dos;
	private org.omg.CORBA.Object obj_inf;
	private NamingContextExt nc;
	
	public ServerPoste(String[] args) throws InvalidName{
		
		this.orb = org.omg.CORBA.ORB.init(args, null);
		// get hold of the naming service
		this.nc = NamingContextExtHelper.narrow(orb
				.resolve_initial_references("NameService"));
	}
	
	public POA getPOA(){
		return poa;
	}
	public NamingContextExt getNameContextExt(){
		return nc;
	}
	public static void main(String [] args){
		
		//Initialize POA and all the servant needed
		
	}
}
