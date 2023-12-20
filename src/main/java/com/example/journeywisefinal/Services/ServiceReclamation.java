package com.example.journeywisefinal.Services;



import com.example.journeywisefinal.Entities.Etat;
import com.example.journeywisefinal.Entities.Reclamation;
import com.example.journeywisefinal.Utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceReclamation implements IService<Reclamation>{

    private Connection con = DataSource.getInstance().getCon();
    private Statement ste;

    public ServiceReclamation() {
        try {
            ste = con.createStatement();
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void add(Reclamation r) throws SQLException {
        String sql = "INSERT INTO `Reclamation` (`id`, `dateReclamation`, `objet`, `description` , `etat`, `id_user` ) VALUES (NULL, '"+r.getDateReclamation()+"', '"+r.getObjet()+"', '"+r.getDescription()+"', '"+r.getEtat()+"', '"+1+"');  ";
        ste.executeUpdate(sql);
        System.out.println("Reclamation Ajouté !");
    }

    @Override
    public void update(Reclamation r) throws SQLException {
        String sql = "UPDATE `Reclamation` SET `dateReclamation` = '"+r.getDateReclamation()+ "', `objet` = '" +r.getObjet()+"', `description` = '"+r.getDescription()+"', `etat` = '"+ r.getEtat()+"' WHERE `id` = "+r.getId();
        ste.executeUpdate(sql);
        System.out.println("Reclamation mise à jour !");
    }



    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM `Reclamation` WHERE `id` = " + id;
        ste.executeUpdate(sql);
        System.out.println("Reclamation supprimée !");
    }


    @Override
    public ArrayList readAll() throws SQLException {
        ArrayList<Reclamation> list = new ArrayList<>();
        try {
            String sql2 = "SELECT * FROM `Reclamation`;";
            ResultSet res = ste.executeQuery(sql2);

            while (res.next()) {
                long id = (res.getLong(1));
                LocalDate dateReclamation = (res.getDate(2).toLocalDate());
                String objet = (res.getString(3));
                String description = (res.getString(4));
                Etat etat = Etat.valueOf(res.getString(5));

                Reclamation r = new Reclamation(id,dateReclamation,objet,description,etat);
                list.add(r);
            }
        }
        catch(SQLException ex) {
            System.out.println(ex);
        }

        return list;

    }

    @Override
    public Reclamation get(int id) throws SQLException {
        String sql = "SELECT * FROM `Reclamation` WHERE `id` = " + id;
        ResultSet res = ste.executeQuery(sql);
        if (res.next()) {
            LocalDate dateReclamation = res.getDate("dateReclamation").toLocalDate();
            String objet = res.getString("objet");
            String description = res.getString("description");
            Etat etat = Etat.valueOf(res.getString("etat"));
            return new Reclamation(id, dateReclamation, objet, description, etat);
        }
        return null;
    }

}
