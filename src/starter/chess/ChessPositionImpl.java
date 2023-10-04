package chess;

public class ChessPositionImpl implements ChessPosition{
  private int row;
  private int collumn;

  public ChessPositionImpl(int row, int collumn){
    this.row = row;
    this.collumn = collumn;
  }

  public void setRow(int row){
    this.row = row;
  }

  public void setCollumn(int collumn){
    this.collumn = collumn;
  }

  @Override
  public int getRow() {
    return row;
  }

  @Override
  public int getColumn() { //letters
    return collumn;
  }
}
