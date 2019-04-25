package pe.progsistem.jesus.ubeprivado.Repositorios;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pe.progsistem.jesus.ubeprivado.R;
import pe.progsistem.jesus.ubeprivado.beans.Lead;

public class LeadsRepository {
    private static LeadsRepository repository = new LeadsRepository();

    private Map<String, Lead> leads = new LinkedHashMap<String, Lead >();

    public static LeadsRepository getInstance() {
        return repository;
    }

    private LeadsRepository() {
        saveLead(new Lead("Lady Gaga", "Pop", "",
                R.drawable.lady));
        saveLead(new Lead("Kathy Perry", "Pop", "",
                R.drawable.kathy));
        saveLead(new Lead("Rihana", "Pop", "",
                R.drawable.rihana));
        saveLead(new Lead("Shakira", "Pop", "",
                R.drawable.shakira));
        saveLead(new Lead("Prince Royce", "Bachata", "",
                R.drawable.prince));
        saveLead(new Lead("Juan Luis Guerra", "Bachata", "",
                R.drawable.juan));
        saveLead(new Lead("Romeo Santos", "Bachata", "",
                R.drawable.romeo));
        saveLead(new Lead("Camilo Sesto", "Balada", "",
                R.drawable.camilo));
        saveLead(new Lead("Nino Bravo", "Balada", "",
                R.drawable.nino));
        saveLead(new Lead("Raphael", "Balada", "",
                R.drawable.rafael));
    }

    private void saveLead(Lead lead) {
        leads.put(lead.getId(), lead);
    }

    public List<Lead> getLeads() {
        return new ArrayList<>(leads.values());
    }
}
