package ingsw.codex_naturalis.model;

public abstract class HandCard {
    private PlayerAreaCard front;
    private PlayerAreaCard back;
    private Boolean showingFront;
    public HandCard(PlayerAreaCard front, PlayerAreaCard back){
        this.front = front;
        this.back = back;
    }
    public Boolean isShowingFront(){ return showingFront; }
    public void showFront(){
        showingFront = true;
    }
    public void showBack(){
        showingFront = false;
    }
    public PlayerAreaCard getFront(){
        return front;
    }
    public PlayerAreaCard getBack(){
        return back;
    }
}
