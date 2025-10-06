package com.example.acti;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.button.MaterialButton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    // Khai báo views
    private TextView tvWelcome, tvLoginTime, tvUsername, tvEmail, tvPhone;
    private ImageView ivAvatar;
    private CardView cvSettings, btnEditAvatar;
    private LinearLayout btnEditProfile, btnChangePassword, btnPrivacy;
    private MaterialButton btnLogout;

    // Dữ liệu từ LoginActivity
    private String username;
    private long loginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Nhận dữ liệu từ Intent
        receiveIntentData();

        // Khởi tạo các views
        initViews();

        // Hiển thị thông tin người dùng
        displayUserInfo();

        // Xử lý các sự kiện click
        setupClickListeners();
    }

    /**
     * Nhận dữ liệu từ LoginActivity qua Intent
     */
    private void receiveIntentData() {
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        loginTime = intent.getLongExtra("LOGIN_TIME", System.currentTimeMillis());

        // Kiểm tra nếu không có username
        if (username == null || username.isEmpty()) {
            username = "User";
        }
    }

    /**
     * Khởi tạo tất cả các views từ layout
     */
    private void initViews() {
        // TextViews
        tvWelcome = findViewById(R.id.tvWelcome);
        tvLoginTime = findViewById(R.id.tvLoginTime);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);

        // ImageViews & CardViews
        ivAvatar = findViewById(R.id.ivAvatar);
        cvSettings = findViewById(R.id.cvSettings);
        btnEditAvatar = findViewById(R.id.btnEditAvatar);

        // LinearLayouts - Quick Actions
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnPrivacy = findViewById(R.id.btnPrivacy);

        // MaterialButton
        btnLogout = findViewById(R.id.btnLogout);
    }

    /**
     * Hiển thị thông tin người dùng lên giao diện
     */
    private void displayUserInfo() {
        // Hiển thị tên chào mừng (viết hoa chữ cái đầu)
        String welcomeText = "Xin chào, " + capitalizeFirstLetter(username) + "!";
        tvWelcome.setText(welcomeText);

        // Hiển thị username
        tvUsername.setText(username);

        // Hiển thị thời gian đăng nhập
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault());
        String formattedTime = sdf.format(new Date(loginTime));
        tvLoginTime.setText("Đăng nhập lúc: " + formattedTime);

        // Hiển thị email (tự động tạo từ username)
        String email = username.toLowerCase() + "@example.com";
        tvEmail.setText(email);

        // Hiển thị số điện thoại (dữ liệu mẫu)
        tvPhone.setText("+84 123 456 789");
    }

    /**
     * Viết hoa chữ cái đầu của chuỗi
     */
    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    /**
     * Xử lý tất cả các sự kiện click
     */
    private void setupClickListeners() {

        // ===== HEADER BUTTONS =====

        // Nút Settings (Bánh xe) ở góc trên phải - Click để ĐĂNG XUẤT
        cvSettings.setOnClickListener(v -> {
            showLogoutConfirmDialog();
        });

        // ===== AVATAR =====

        // Nút Edit Avatar (icon bút chì trên avatar)
        btnEditAvatar.setOnClickListener(v -> {
            Toast.makeText(this, "Chọn ảnh đại diện mới", Toast.LENGTH_SHORT).show();
            openImagePicker();
        });

        // Click vào avatar để xem phóng to
        ivAvatar.setOnClickListener(v -> {
            Toast.makeText(this, "Xem ảnh đại diện", Toast.LENGTH_SHORT).show();
            // TODO: Mở dialog xem ảnh phóng to
            // showAvatarDialog();
        });

        // ===== QUICK ACTIONS =====

        // Nút Chỉnh sửa Profile
        btnEditProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Chỉnh sửa thông tin cá nhân", Toast.LENGTH_SHORT).show();
            // TODO: Chuyển đến EditProfileActivity
            // Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            // intent.putExtra("USERNAME", username);
            // startActivity(intent);
        });

        // Nút Đổi mật khẩu
        btnChangePassword.setOnClickListener(v -> {
            Toast.makeText(this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show();
            // TODO: Chuyển đến ChangePasswordActivity
            // Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
            // startActivity(intent);
        });

        // Nút Bảo mật và Quyền riêng tư
        btnPrivacy.setOnClickListener(v -> {
            Toast.makeText(this, "Cài đặt bảo mật", Toast.LENGTH_SHORT).show();
            // TODO: Chuyển đến PrivacySettingsActivity
            // Intent intent = new Intent(ProfileActivity.this, PrivacyActivity.class);
            // startActivity(intent);
        });

        // ===== LOGOUT BUTTON =====

        // Nút Đăng xuất lớn ở cuối trang
        btnLogout.setOnClickListener(v -> showLogoutConfirmDialog());
    }

    /**
     * Hiển thị dialog xác nhận đăng xuất
     */
    private void showLogoutConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi tài khoản?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Đăng xuất", (dialog, which) -> performLogout())
                .setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(this, "Đã hủy đăng xuất", Toast.LENGTH_SHORT).show();
                })
                .setCancelable(true)
                .show();
    }

    /**
     * Thực hiện đăng xuất và quay về LoginActivity
     */
    private void performLogout() {
        // Xóa thông tin "Ghi nhớ đăng nhập" nếu cần
        // SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        // SharedPreferences.Editor editor = prefs.edit();
        // editor.putBoolean("remember", false);
        // editor.remove("username");
        // editor.remove("password");
        // editor.apply();

        Toast.makeText(this, "Đăng xuất thành công! Hẹn gặp lại " + username,
                Toast.LENGTH_SHORT).show();

        // Chuyển về màn hình đăng nhập và xóa toàn bộ back stack
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Kết thúc ProfileActivity
        finish();
    }

    /**
     * Mở Image Picker để chọn ảnh đại diện
     */
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    /**
     * Xử lý kết quả khi chọn ảnh
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            try {
                // Hiển thị ảnh được chọn lên avatar
                ivAvatar.setImageURI(data.getData());
                Toast.makeText(this, "Đã cập nhật ảnh đại diện", Toast.LENGTH_SHORT).show();

                // TODO: Upload ảnh lên server hoặc lưu vào local storage
                // uploadAvatar(data.getData());

            } catch (Exception e) {
                Toast.makeText(this, "Lỗi khi chọn ảnh: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Xử lý khi người dùng nhấn nút Back
     */
    @Override
    public void onBackPressed() {
        // Hiển thị dialog xác nhận
        new AlertDialog.Builder(this)
                .setTitle("Thoát ứng dụng")
                .setMessage("Bạn có muốn đăng xuất và quay lại màn hình đăng nhập?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Đăng xuất", (dialog, which) -> performLogout())
                .setNegativeButton("Ở lại", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setNeutralButton("Thoát app", (dialog, which) -> {
                    // Thoát hoàn toàn khỏi ứng dụng
                    finishAffinity();
                })
                .show();
    }

    /**
     * Dọn dẹp khi Activity bị hủy
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Giải phóng resources nếu cần
    }

    /**
     * Được gọi khi Activity quay lại foreground
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật thông tin nếu cần
    }
}