using ServiceReference1;
using System;
using System.IO;
using System.Threading.Tasks;

class Program
{
    static async Task Main(string[] args)
    {
        Console.WriteLine("SOAP Client!");
        string filePath = "output.xml";

        try
        {
            SOAPInterfaceClient client = new SOAPInterfaceClient();
            var response = await client.getDataAsync();
            Console.WriteLine("Response received:");
            
            string xmlContent = response.Body.@return;
            File.WriteAllText(filePath, xmlContent);

            Console.WriteLine("Plik XML został zapisany jako: " + Path.GetFullPath(filePath));
        }
        catch (Exception ex)
        {
            Console.WriteLine("Błąd: " + ex.Message);
        }
    }
}
