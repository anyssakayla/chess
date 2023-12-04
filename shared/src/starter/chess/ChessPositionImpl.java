package chess;

import java.util.Objects;

public class ChessPositionImpl implements ChessPosition{
  private int row;
  private int collumn;

  public ChessPositionImpl(int row, int collumn){
    this.row = row;
    this.collumn = collumn;
  }

  @Override
  public void setRow(int row){
    this.row = row;
  }

  @Override
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


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChessPositionImpl that=(ChessPositionImpl) o;
    return row == that.row && collumn == that.collumn;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, collumn);
  }
}
