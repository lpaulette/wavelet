import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<>();

    public String handleRequest(URI url) {

        if (url.getPath().equals("/")) {
            return String.format("List: %s", list);
        }

        else if(url.getPath().contains("/search")){
            String[] searchParameters = url.getQuery().split("=");
            if (searchParameters[0].equals("s")){
                String toSearch = searchParameters[1];
                String [] toReturn = new String[list.size()];
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).contains(toSearch)){
                        toReturn[i] = list.get(i);
                    }
                }
                return String.format("%s", Arrays.toString(toReturn));
            } 
        }

        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    list.add(parameters[1]);
                    return String.format("%s was added to the list!", parameters[1]);
                }
            }
            
            return "404 Not Found!";
        }

        return null;
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
