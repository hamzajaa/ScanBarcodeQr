package com.example.scanbarcodeqr;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    Button scanButton;
    Button scanButton2;
    TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scanButton = findViewById(R.id.scanButton);
        textView = findViewById(R.id.textView);
        scanButton2 = findViewById(R.id.scanButton2);

        scanButton2.setOnClickListener(v -> {
            // Code to scan barcode or QR code
            System.out.println("Scanning...");
            ScanOptions scanOptions = new ScanOptions();
            scanOptions.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES);
            scanOptions.setPrompt("Scan a barcode");
            scanOptions.setCameraId(0);  // Use a specific camera of the device
            scanOptions.setBeepEnabled(false);
            scanOptions.setBarcodeImageEnabled(true);
            barcodeLauncher.launch(scanOptions);
        });

        scanButton.setOnClickListener(v -> {
            // Code to scan barcode or QR code
            System.out.println("Scanning...");
            ScanOptions scanOptions = new ScanOptions();
            scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            scanOptions.setPrompt("Scan a QrCode");
            scanOptions.setCameraId(0);  // Use a specific camera of the device
            scanOptions.setBeepEnabled(false);
            scanOptions.setBarcodeImageEnabled(true);
            barcodeLauncher.launch(scanOptions);
        });
    }

    public final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
                    textView.setText(result.getContents());

                    ClipboardManager clipboard = (ClipboardManager)
                            getSystemService(Context.CLIPBOARD_SERVICE);
                    // Creates a new text clip to put on the clipboard.
                    ClipData clip = ClipData.newPlainText("simple text", result.getContents());
                    // Set the clipboard's primary clip.
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                }
            });


}