package com.GotYourBac.gotYourBac.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    public List<Drink> drinkList;

    String username;
    String password;
    String firstName;
    String lastName;
    String gender;
    double height;
    float weight;
    String profilepic;

    public ApplicationUser() {
    }

    public ApplicationUser(String username, String password, String firstName, String lastName, String gender, double height, float weight, String profilepic) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.profilepic = profilepic;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public double getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public String getProfilepic() {
        return profilepic;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }


//    totalAlcoholContent {
//        drinkSizeInGrams = sizeofDrink * 28.3494;
//        Return alcohollContent * drinkSizeInGrams;

    public float getTotalAlcoholContent(float drinkSize, float alcoholContent) {
        float drinkSizeInGrams;
        drinkSizeInGrams = drinkSize * 28.3494f;
        return (alcoholContent /100) * drinkSizeInGrams;
    }
    public float calculateBAC() {
        float BAC;
        float genderConstant;
        float alcoholContent = 0.0f;

        for(Drink drink : this.drinkList){
            alcoholContent += getTotalAlcoholContent(drink.drinkSize, drink.getStrABV());

        }

        if(this.gender.equals("male")) {
            genderConstant = 0.73f;

        } else if(this.gender.equals("female")) {
            genderConstant = 0.66f;
        } else {
            genderConstant = 0.69f;
        }
        float weightInGrams = this.weight + 453.592f;
        float temp = weightInGrams * genderConstant;
        BAC = (alcoholContent / temp);

        return BAC;
    }

    public String getBACChart(float BAC) {
        String bacEffects;

        if(BAC < .05) {
            bacEffects = "Effects: Talkative , more relaxed, and more confident. In Washington State, you are legally allowed to drive with this BAC." ;
        } else if(BAC > .05 && BAC < .08) {
            bacEffects = "Effects: Impaired judgement, and reduce inhibitions. In Washington State, you are legally allowed to drive with this BAC.";
        } else if(BAC >= .08 && BAC < .15 ) {
            bacEffects = "Effects: Slurred speech, impaired balance and coordination, unstable emotions and possibly nausea, and vomiting. In Washington State, you can be charged with DUI if your BAC level is at or above 0.08%.";

        } else if(BAC > .15 && BAC < .30) {
            bacEffects = "Effects: Inadequate breathing, unable to walk without assistance, loss of bladder control and possibly loss of consciousness. In Washington State, you can be charged with DUI if your BAC level is at or above 0.08%.";
        } else {
            bacEffects = "Effects: Possibly see you in the afterlife or the ER. In Washington State, you can be charged with DUI if your BAC level is at or above 0.08%.";
        }
        return bacEffects;
    }

}
