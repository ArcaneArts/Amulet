

/*
 * Copyright (c) 2019 - Manifold Systems LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package art.arcane.amulet.range;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class IntegerRange extends NumberRange<Integer, IntegerRange>
{
  public IntegerRange( Integer left, Integer right )
  {
    this( left, right, 1 );
  }
  public IntegerRange( Integer left, Integer right, int step )
  {
    this( left, right, step, true, true, false );
  }
  public IntegerRange( Integer left, Integer right, int step, boolean leftClosed, boolean rightClosed, boolean reverse )
  {
    super( left, right, step, leftClosed, rightClosed, reverse );

    if( step <= 0 )
    {
      throw new IllegalArgumentException( "The step must be greater than 0: " + step );
    }
  }

  public IntegerRange unaryMinus()
  {
    int left = getLeftEndpoint();
    int right = getRightEndpoint();

    if(isReversed())
    {
      return new IntegerRange(left, right, getStep(), true, true, false);
    }

    return new IntegerRange(right, left, getStep(), true, true, true);
  }

  @Override
  public Iterator<Integer> iterateFromLeft()
  {
    return new ForwardIterator();
  }

  @Override
  public Iterator<Integer> iterateFromRight()
  {
    return new ReverseIterator();
  }

  @Override
  public Integer getFromLeft( int iStepIndex )
  {
    if( iStepIndex < 0 )
    {
      throw new IllegalArgumentException( "Step index must be >= 0: " + iStepIndex );
    }

    if( !isLeftClosed() )
    {
      iStepIndex++;
    }    
    int value = getLeftEndpoint() + getStep() * iStepIndex;
    if( isRightClosed() ? value <= getRightEndpoint() : value < getRightEndpoint() )
    {
      return value;
    }

    return null;
  }

  @Override
  public Integer getFromRight( int iStepIndex )
  {
    if( iStepIndex < 0 )
    {
      throw new IllegalArgumentException( "Step index must be >= 0: " + iStepIndex );
    }

    if( !isRightClosed() )
    {
      iStepIndex++;
    }    
    int value = getRightEndpoint() - getStep() * iStepIndex;
    if( isLeftClosed() ? value >= getLeftEndpoint() : value > getLeftEndpoint() )
    {
      return value;
    }

    return null;
  }

  public class ForwardIterator extends AbstractIntIterator
  {
    private int _csr;

    public ForwardIterator()
    {
      _csr = getLeftEndpoint();
      if( !isLeftClosed() && hasNext() )
      {
        next();
      }
    }

    @Override
    public boolean hasNext()
    {
      return _csr < getRightEndpoint() || (isRightClosed() && _csr == getRightEndpoint());
    }

    @Override
    public Integer next()
    {
      return nextInt();
    }

    public int nextInt()
    {
      if( _csr > getRightEndpoint() ||
          (!isRightClosed() && _csr == getRightEndpoint()) )
      {
        throw new NoSuchElementException();
      }
      int ret = _csr;
      _csr = _csr + getStep();
      return ret;
    }

    @Override
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }

  private class ReverseIterator extends AbstractIntIterator
  {
    private int _csr;

    public ReverseIterator()
    {
      _csr = getRightEndpoint();
      if( !isRightClosed() && hasNext() )
      {
        next();
      }
    }

    @Override
    public boolean hasNext()
    {
       return _csr > getLeftEndpoint() || (isLeftClosed() && _csr == getLeftEndpoint());
    }

    @Override
    public Integer next()
    {
      return nextInt();
    }

    public int nextInt()
    {
      if( _csr < getLeftEndpoint() ||
          (!isLeftClosed() && _csr == getLeftEndpoint()) )
      {
        throw new NoSuchElementException();
      }
      int ret = _csr;
      _csr = _csr - getStep();
      return ret;
    }

    @Override
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }
}
