package test

class Tile {
	
	private char id
	private int src_x, src_y
	private int count // number of images for the same animated tile
	
	private Tile(char id, int src_x, int src_y, int count=1) {
		this.id = id
		this.src_x = src_x
		this.src_y = src_y
		this.count = count
	}
	
	public static Tile SUN    = new Tile("*", 0, 0)
	public static Tile WATER  = new Tile("W", 1, 0, 2)
	public static Tile GRASS  = new Tile("G", 3, 0, 2)
	public static Tile BUSH   = new Tile("B", 5, 0)
	public static Tile SWAMP  = new Tile("S", 6, 0)
	public static Tile FOREST = new Tile("f", 7, 0)
	public static Tile DENSE_FOREST = new Tile("F", 7, 0)
	
}
