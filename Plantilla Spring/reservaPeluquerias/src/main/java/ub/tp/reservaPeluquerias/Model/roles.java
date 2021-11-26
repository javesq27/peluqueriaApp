package ub.tp.reservaPeluquerias.Model;

public enum roles
{
    USER("Empleado"),
    ADMIN("Administrador");

    private String displayValue;
    
    private roles(String displayValue) 
    {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() 
    {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) 
    {
        this.displayValue = displayValue;
    }
}
