package fxutils;

import javafx.scene.transform.Affine;
import java.awt.geom.AffineTransform;

public class TransformUtils {


    public static AffineTransform fxToAwtAffine(javafx.scene.transform.Transform fxTran) {
        return new AffineTransform(fxTran.getMxx(), fxTran.getMyx(), fxTran.getMxy(), fxTran.getMyy(), fxTran.getTx(), fxTran.getTy());
    }

    public static Affine awtToFxAffine(AffineTransform tran) {
        return javafx.scene.transform.Transform.affine(tran.getScaleX(), tran.getScaleY(), tran.getShearY(), tran.getShearX(), tran.getTranslateX(), tran.getTranslateY());
    }

    public static double[] awtToFxTransformArray(AffineTransform tran) {
        double[] mat = new double[6];
        tran.getMatrix(mat);
        return mat;
    }




}
