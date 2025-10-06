package com.example.acti;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private TextInputLayout usernameLayout, passwordLayout;
    private MaterialButton btnLogin, btnBiometric;
    private CheckBox cbRememberMe;
    private TextView tvForgotPassword, tvSignUp;
    private CardView btnChangeAvatar, btnGoogle, btnFacebook, btnTwitter;

    // Định nghĩa tài khoản và mật khẩu
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "123456";

    // SharedPreferences để lưu thông tin "Ghi nhớ đăng nhập"
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Khởi tạo views
        initViews();

        // Load thông tin đã lưu (nếu có)
        loadSavedCredentials();

        // Xử lý sự kiện
        setupClickListeners();
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        usernameLayout = findViewById(R.id.usernameLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        btnLogin = findViewById(R.id.btnLogin);
        btnBiometric = findViewById(R.id.btnBiometric);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnTwitter = findViewById(R.id.btnTwitter);
    }

    private void loadSavedCredentials() {
        boolean remember = sharedPreferences.getBoolean(KEY_REMEMBER, false);
        if (remember) {
            String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
            String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");
            etUsername.setText(savedUsername);
            etPassword.setText(savedPassword);
            cbRememberMe.setChecked(true);
        }
    }

    private void setupClickListeners() {
        // Xử lý nút Đăng nhập
        btnLogin.setOnClickListener(v -> handleLogin());

        // Xử lý nút Đăng nhập vân tay
        btnBiometric.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng đăng nhập sinh trắc học đang phát triển",
                    Toast.LENGTH_SHORT).show();
            // TODO: Implement biometric authentication
        });

        // Xử lý Quên mật khẩu
        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng khôi phục mật khẩu",
                    Toast.LENGTH_SHORT).show();
            // TODO: Navigate to ForgotPasswordActivity
        });

        // Xử lý Đăng ký
        tvSignUp.setOnClickListener(v -> {
            Toast.makeText(this, "Chuyển đến màn hình đăng ký",
                    Toast.LENGTH_SHORT).show();
            // TODO: Navigate to SignUpActivity
        });

        // Xử lý đổi avatar
        btnChangeAvatar.setOnClickListener(v -> {
            Toast.makeText(this, "Chọn ảnh đại diện", Toast.LENGTH_SHORT).show();
            // TODO: Open image picker
        });

        // Xử lý đăng nhập Google
        btnGoogle.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng nhập bằng Google", Toast.LENGTH_SHORT).show();
            // TODO: Implement Google Sign-In
        });

        // Xử lý đăng nhập Facebook
        btnFacebook.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng nhập bằng Facebook", Toast.LENGTH_SHORT).show();
            // TODO: Implement Facebook Login
        });

        // Xử lý đăng nhập Twitter
        btnTwitter.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng nhập bằng Twitter", Toast.LENGTH_SHORT).show();
            // TODO: Implement Twitter Login
        });
    }

    /**
     * Xử lý đăng nhập và chuyển sang ProfileActivity
     */
    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Reset error messages
        usernameLayout.setError(null);
        passwordLayout.setError(null);

        // Kiểm tra dữ liệu nhập
        if (username.isEmpty()) {
            usernameLayout.setError("Vui lòng nhập tên đăng nhập");
            etUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordLayout.setError("Vui lòng nhập mật khẩu");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordLayout.setError("Mật khẩu phải có ít nhất 6 ký tự");
            etPassword.requestFocus();
            return;
        }

        // Kiểm tra tài khoản và mật khẩu
        if (username.equals(VALID_USERNAME) && password.equals(VALID_PASSWORD)) {
            // Lưu thông tin nếu chọn "Ghi nhớ đăng nhập"
            saveCredentials(username, password);

            // Đăng nhập thành công
            Toast.makeText(this, "Đăng nhập thành công! Chào mừng " + username,
                    Toast.LENGTH_SHORT).show();

            // QUAN TRỌNG: Chuyển sang màn hình Profile và truyền dữ liệu
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            intent.putExtra("USERNAME", username);
            intent.putExtra("LOGIN_TIME", System.currentTimeMillis());
            startActivity(intent);

            // Kết thúc LoginActivity để không quay lại được khi nhấn Back
            finish();

        } else {
            // Đăng nhập thất bại
            passwordLayout.setError("Sai tên đăng nhập hoặc mật khẩu!");
            etPassword.setError("Thông tin đăng nhập không đúng");
            etPassword.requestFocus();

            Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void saveCredentials(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (cbRememberMe.isChecked()) {
            editor.putBoolean(KEY_REMEMBER, true);
            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_PASSWORD, password);
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_USERNAME);
            editor.remove(KEY_PASSWORD);
        }

        editor.apply();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Toast.makeText(this, "Ảnh đã được chọn", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear sensitive data if not remembered
        if (cbRememberMe != null && !cbRememberMe.isChecked()) {
            if (etPassword != null) {
                etPassword.setText("");
            }
        }
    }
}