
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author how32
 */
public class WMBarcode {
    //바코드 처리(바코드 생성 + 이미지 생성 등)
    private static WMBarcode barcode_instance = null;
    private Component component = null;
    private String fileName = null;
    
    private WMBarcode() {}
    
    public static WMBarcode getInstance() {
        if(barcode_instance == null) {
            barcode_instance = new WMBarcode();
        }
        
        return barcode_instance;
    }
    
    public void setComponent(Component component) {
        this.component = component;
    }
    
    public void create_barcode(String product_code, String product_name) {
        //제품코드, 제품명 입력하면 생성
        if(fileName == null || fileName.equals("")) { dir_select(); }
        try {
            Barcode barcode = BarcodeFactory.createCode128(product_code+"/"+product_name);
            barcode.setDrawingText(false);
            barcode.setBarHeight(50);
            File file = new File(fileName+"/"+product_name+".png");
            BarcodeImageHandler.savePNG(barcode, file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void dir_select() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //디렉토리만 선택할 수 있도록 설정함
        
        fileChooser.showDialog(component, null);
        File dir = fileChooser.getSelectedFile(); //디렉토리 선택 시 반환
        fileName = dir.getAbsolutePath();
    }
}
