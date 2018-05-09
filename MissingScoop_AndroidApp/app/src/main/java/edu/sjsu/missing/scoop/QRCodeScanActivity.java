package edu.sjsu.missing.scoop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class QRCodeScanActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int requestCameraPermissionId = 1001;
    boolean alreadyScanned = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alreadyScanned = false;
        setContentView(R.layout.activity_qrcode_scan);
        scanBarcode();
    }


    @Override
    protected void onResume() {
        super.onResume();
        alreadyScanned = false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        alreadyScanned = false;
    }

    public void scanBarcode() {
        cameraPreview = findViewById(R.id.surfaceView);

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).setFacing(CameraSource.CAMERA_FACING_BACK).build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(QRCodeScanActivity.this, new String[]{Manifest.permission.CAMERA}, requestCameraPermissionId);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    Log.e("QRCodeScanActivity", e.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0 && !alreadyScanned) {
                    alreadyScanned = true;

                    String qrCodeValue = qrCodes.valueAt(0).displayValue;
                    Log.d("QRCodeScanActivity", qrCodeValue);

                    setActivityResult(qrCodeValue);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case requestCameraPermissionId:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        Log.e("QRCodeScanActivity", e.getMessage());
                    }
                }
                break;
        }
    }

    public void setActivityResult(final String qrCode) {
        Intent intent = new Intent();
        intent.putExtra("QRCode", qrCode);
        setResult(2, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }
}
