package src.civ;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;

import static src.civ.State.TileState.TileSelected;
import static src.civ.State.TileState.TileUnSelected;
import static src.civ.State.CityState.CitySelected;
import static src.civ.State.CityState.CityUnSelected;
import static src.civ.State.UnitState.UnitSelected;
import static src.civ.State.UnitState.UnitUnSelected;

public class Tile implements Comparable<Tile>{
    private boolean selected, hilight, explored, plain;

    private int countToFog;
    private int x, y;

    private TerrainType terrain;
    private PhysicalUnit unit;
    private City city;
    private TileView view;

    private int[][] offsets = {{-1,-1},
                            {0,-1},
                            {1,0},
                            {1,1},
                            {0,1},
                            {-1,0}};

    public Tile(){
        // Empty constructor 
    }
    public Tile(TerrainType tt, PhysicalUnit pu, int x, int y){
        init(tt, pu, null, x, y);
    }
    public Tile(TerrainType tt, int x, int y){
        init(tt, null, null, x, y);
    }
    public Tile (TerrainType tt, City city, int x, int y) {
        init(tt, null, city, x, y);

    }

    private void init(TerrainType tt, PhysicalUnit pu, City city, int x, int y){
        terrain = tt;
        unit = pu;
        this.city = city;
        this.x = x;
        this.y = y;

        hilight = false;
        selected = false;
        explored = false;
        countToFog = 0;
        /**
         * pixelx = ((x - y) * bredd) + offsetX
         * pixely = ((x + y) * height / 2) + offsetY
         */

        view = new TileView(((x - y)*120)+120*GameMap.getInstance().getWidth(), ((x + y)*68)+170, this);

        setUnit(pu);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void select(){
        State st = State.getInstance();
        if(st.getTileState() == TileSelected){
            State.getSelectedTile().deselect();
        }
        st.setSelectedTile(this);
        st.setTileState(TileSelected);
        if(hasCity()){
        	st.setSelectedCity(city);
        	st.setCityState(CitySelected);
        }
        if(hasUnit()){
            st.setSelectedUnit(unit);
            st.setUnitState(UnitSelected);
        }
        selected = true;
        view.repaint();
    }

    public void deselect(){
        State st = State.getInstance();
        if(st.getSelectedTile().equals(this)){
            st.setSelectedTile(null);
            st.setTileState(TileUnSelected);
            selected = false;
            if(hasCity()){
                st.setSelectedCity(null);
                st.setCityState(CityUnSelected);
            }
            if(hasUnit()){
                st.setSelectedUnit(null);
                st.setUnitState(UnitUnSelected);
            }
            view.repaint();
        }
    }

    public boolean isSelected(){
        return selected;
    }
    
    public void setCity(City city){
        this.city = city;
    }
    

    //This function must be edited 
    public void setUnit(PhysicalUnit pu){
        if(pu != null && pu.isAlly()){
            setExplored(true);
            if(GameMap.getInstance() != null){
                for(Tile t : GameMap.getInstance().getNeighbours(this, pu.getType().getVision())){
                    t.setExplored(true);
                    t.getView().repaint();
                }
            }
        }else{
            //countToFog = 0;
        }
        unit = pu;
        view.repaint();
    }

    public PhysicalUnit getUnit(){
        return unit; 
    }
    
    public City getCity() {
    	return city;	
    }

    public boolean hasCity() {
    	return city!=null;	
    }
    
    public boolean hasUnit(){
        return unit != null;
    }

    public BufferedImage getUnitImg(){
        return unit.getImage();
    }
    
    public BufferedImage getTileImg(){
        return terrain.getNormalImage();
    }

    public BufferedImage getTileFogImg(){
        return terrain.getFogImage();
    }

    public TileView getView(){
        return view;
    }

    public TerrainType getTerrain(){
        return terrain;
    }

    public void hilight(){
        hilight = true;
    }

    public void dehilight(){
        hilight = false;
    }

    public boolean isHilighted(){
        return hilight;
    }

    //Must be edited later
    public void setExplored(boolean bool){
        countToFog = 1;
        explored = bool;
    }

    public boolean isExplored(){
        return explored;
    }

    public boolean hasFog(){
        return countToFog == 0;
    }

    
    public ArrayList<int[]> getNeighbours(int range){
        if(range < 1){
            return new ArrayList<int[]>();
        }
        int x = getX();
        int y = getY();

        ArrayList<int[]> acc = new ArrayList<int[]>();

        for(int[] off : offsets){
            int[] t = {x - off[0], y - off[1]}; //Tile t = getTile(x - off[0], y - off[1]);
            if(t != null)
                acc.add(t);
        }
        if(range > 1){
            for(int[] off : offsets){
                int[] t = {x - off[0], y - off[1]}; //Tile t = getTile(x - off[0], y - off[1]);
                acc.addAll(getNeighbours(t, range - 1, acc));
            }
        }

        return acc;
    }

    // Get neighbours within a range of 1 of the tile.
    private ArrayList<int[]> getNeighbours(int[] tile){
        ArrayList<int[]> tmp = new ArrayList<int[]>();
        for(int[] off : offsets){
            int[] t = {x - off[0], y - off[1]}; //Tile t = getTile(x - off[0], y - off[1]);
            if(t != null)
                tmp.add(t);
        }
        return tmp;
    }

    private ArrayList<int[]> getNeighbours(int[] tile, int range, ArrayList<int[]> acc){
        if(range < 1){
            return new ArrayList<int[]>();
        }
        int x = tile[0];
        int y = tile[1];

        ArrayList<int[]> tmp = new ArrayList<int[]>();

        for(int[] t2 : getNeighbours(tile)){
            if(!acc.contains(t2))
                tmp.add(t2);
        }
        if(range > 1){
            for(int[] off : offsets){
                int[] t = {x - off[0], y - off[1]}; //Tile t = getTile(x - off[0], y - off[1]);
                acc.addAll(getNeighbours(t, range - 1, acc));
            }
        }

        return acc;            
    }

    public boolean equals(Object other){
        if(other instanceof Tile){
            Tile oth = (Tile)other;
            if(this.x == oth.x && this.y == oth.y){
                return true;
            }
        }
        return false;
    }

    public int compareTo(Tile other){
        return (this.x * this.y) - (other.x * other.y);
    }
}
