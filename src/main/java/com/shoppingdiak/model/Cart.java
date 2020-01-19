/**
 * 
 */
package com.shoppingdiak.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author HP
 *
 */
@Entity
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
    Long id;
	
	@Column(name = "nom", nullable = true, updatable = true)
    String nom;
	
	@Column(name = "prenom", nullable = true, updatable = true)
    String prenom;
	
	@Column(name = "email", nullable = false, updatable = true)
    String email;
	
	@Column(name = "adresse", nullable = true, updatable = true)
    String adresse;
	
	@Column(name = "idproduit", nullable = false, updatable = true)
    long idproduit;
	
	@Column(name = "quantite", nullable = false, updatable = true)
    int quantite;
	
	@Column(name = "prixUnitaire", nullable = false, updatable = true)
    double prixUnitaire;

	@Column(name = "datePaiement", columnDefinition = "DATETIME DEFAULT NOW()" ,nullable = true, updatable = true)
    Date datePaiement;
	
	/**
	 * @return the prixUnitaire
	 */
	public double getPrixUnitaire() {
		return prixUnitaire;
	}

	/**
	 * @param prixUnitaire the prixUnitaire to set
	 */
	public void setPrixUnitaire(double prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the idproduit
	 */
	public long getIdproduit() {
		return idproduit;
	}

	/**
	 * @param idproduit the idproduit to set
	 */
	public void setIdproduit(long idproduit) {
		this.idproduit = idproduit;
	}

	/**
	 * @return the quantite
	 */
	public int getQuantite() {
		return quantite;
	}

	/**
	 * @param quantite the quantite to set
	 */
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	/**
	 * @return the datePaiement
	 */
	public Date getDatePaiement() {
		return datePaiement;
	}

	/**
	 * @param datePaiement the datePaiement to set
	 */
	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	/**
	 * 
	 */
	public Cart() {
		super();
	}



	

}
