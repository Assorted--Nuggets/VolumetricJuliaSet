package math;

public class Matrix
{
    int nRows, nCols;

    float[][] vals;

    public static Matrix loadIdentity(Matrix dest)
    {
        for(int i = 0; i < dest.nRows && i < dest.nCols; i++)
        {
            dest.setElement(1, i, i);
        }
        return dest;
    }

    public static Matrix loadZeros(Matrix dest)
    {
        for(int i = 0; i < dest.nRows; i++)
        {
            for(int j = 0; j < dest.nCols; j++)
            {
                dest.setElement(0, i, j);
            }
        }
        return dest;
    }

    public Matrix(int rows, int cols)
    {
        this.nRows = rows;
        this.nCols = cols;

        vals = new float[nRows][nCols];
    }

    public Matrix loadIdentity()
    {
        for(int i = 0; i < nRows && i < nCols; i++)
        {
            vals[i][i] = 1;
        }
        return this;
    }

    public Matrix loadZeros()
    {
        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nCols; j++)
            {
                vals[i][j] = 0;
            }
        }
        return this;
    }

    public float[] toArray()
    {
        float[] res = new float[nRows * nCols];

        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nCols; j++)
            {
                res[i*nRows + j] = vals[i][j];
            }
        }
        return res;
    }

    public Matrix transpose(Matrix dest)
    {
        dest.vals = new float[nCols][nRows];

        for(int i = 0; i < nCols; i++)
        {
            for(int j = 0; j < nRows; j++)
            {
                dest.vals[i][j] = vals[j][i];
            }
        }
        return dest;
    }

    public Matrix transposeInPlace()
    {
        float[][] temp = new float[nCols][nRows];

        for(int i = 0; i < nCols; i++)
        {
            for(int j = 0; j < nRows; j++)
            {
                temp[i][j] = vals[j][i];
            }
        }
        this.vals = temp;
        return this;
    }

    public Matrix add(Matrix m)
    {
        Matrix res = new Matrix(nRows, nCols);

        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nCols; j++)
            {
                res.setElement(getElement(i,j) - m.getElement(i,j), i, j);
            }
        }
        return res;
    }

    public Matrix add(Matrix m, Matrix dest)
    {
        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nCols; j++)
            {
                dest.setElement(getElement(i,j) + m.getElement(i,j), i, j);
            }
        }
        return dest;
    }

    public Matrix sub(Matrix m)
    {
        Matrix res = new Matrix(nRows, nCols);
        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nCols; j++)
            {
                res.setElement(getElement(i,j) - m.getElement(i,j), i, j);
            }
        }
        return res;
    }

    public Matrix sub(Matrix m, Matrix dest)
    {
        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nCols; j++)
            {
                dest.setElement(getElement(i,j) - m.getElement(i,j), i, j);
            }
        }
        return dest;
    }

    public Matrix mul(Matrix m)
    {
        if((nCols != m.nRows))
        {
            throw new ArithmeticException("Cannot multiply matrices, shape mismatch");
        }

        Matrix res = new Matrix(nRows, nCols);

        for(int i = 0; i < res.nRows; i++)
        {
            // Take the i-th row of this matrix, and j-th column of m, compute dot product of the two
            // Then place result in res[i][j]

            for(int j = 0; j < res.nCols; j++)
            {
                float v = 0;

                for(int k = 0; k < res.nCols; k++)
                {
                    v += vals[i][k] * m.vals[k][j];
                }
                res.setElement(v, i, j);
            }
        }
        return res;
    }

    public Matrix mul(Matrix m, Matrix dest)
    {
        if((nCols != m.nRows))
        {
            throw new ArithmeticException("Cannot multiply matrices, shape mismatch");
        }

        Matrix res = new Matrix(nRows, nCols);

        for(int i = 0; i < res.nRows; i++)
        {
            // Take the i-th row of this matrix, and j-th column of m, compute dot product of the two
            // Then place result in res[i][j]

            for(int j = 0; j < res.nCols; j++)
            {
                float v = 0;

                for(int k = 0; k < res.nCols; k++)
                {
                    v += vals[i][k] * m.vals[k][j];
                }
                res.setElement(v, i, j);
            }
        }
        dest.vals = res.vals;
        return dest;
    }

    public Matrix mul(float s)
    {
        Matrix res = new Matrix(nRows, nCols);
        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nRows; j++)
            {
                res.setElement(getElement(i,j)*s, i, j);
            }
        }
        return res;
    }

    /**
     * Element wise scalar multiplication, stores into existing Matrix, for cases where we have an existing Matrix and we don't
     * want to waste time on object creation, also useful for applying operations on the selected matrix
     * @param s scalar to multiply by
     * @param dest the Matrix that we want to store the result in
     * @return dest Matrix so we have a return point
     */
    public Matrix mul(float s, Matrix dest)
    {
        Matrix res = new Matrix(nRows, nCols);
        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nRows; j++)
            {
                res.setElement(getElement(i,j)*s, i, j);
            }
        }
        dest.vals = res.vals;
        return dest;
    }

    /**
     * Element wise scalar division, creates new Matrix
     * @param s divisor
     * @return result
     */
    public Matrix div(float s)
    {
        return this.mul(1/s);
    }

    /**
     *
     * @param s number to divide each element by
     * @param dest Matrix to put the resultant values into
     * @return dest so that operations can be chained
     */
    public Matrix div(float s, Matrix dest)
    {
        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nRows; j++)
            {
                dest.setElement(getElement(i,j)/s, i, j);
            }
        }
        return dest;
    }

    public Matrix copyTo(Matrix dest)
    {
        dest.vals = this.vals.clone();
        return dest;
    }


    public Matrix addInPlace(Matrix m)
    {
        return this.add(m, this);
    }

    public Matrix subInPlace(Matrix m)
    {
        return this.sub(m, this);
    }

    public Matrix mulInPlace(Matrix m)
    {
        return this.mul(m, this);
    }

    public Matrix mulInPlace(float s)
    {
        return this.mul(s, this);
    }

    public Matrix divInPlace(float s)
    {
        return this.div(s, this);
    }

    public float getElement(int r, int c)
    {
        return vals[r][c];
    }

    public void setElement(float value, int r, int c)
    {
        vals[r][c] = value;
    }

    public boolean isSameSize(Matrix m)
    {
        return m.nCols == nCols && m.nRows == nRows;
    }

    public boolean isSquare()
    {
        return nRows == nCols;
    }


    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < nRows; i++)
        {
            for(int j = 0; j < nCols; j++)
            {
                sb.append(vals[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
