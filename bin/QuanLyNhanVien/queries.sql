-- Tạo cơ sở dữ liệu
CREATE DATABASE QuanLyNhanVien;

-- Sử dụng cơ sở dữ liệu mới tạo
USE QuanLyNhanVien;

-- Tạo bảng Employee
CREATE TABLE Employee (
    employee_id VARCHAR(50) PRIMARY KEY,  -- ID nhân viên
    full_name VARCHAR(255) NOT NULL,      -- Tên đầy đủ
    username VARCHAR(50) UNIQUE NOT NULL, -- Tên đăng nhập (unique)
    password VARCHAR(255) NOT NULL,       -- Mật khẩu
    role VARCHAR(50),                     -- Vai trò
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Thời gian tạo
);

-- Tạo bảng Log
CREATE TABLE Log (
    log_id VARCHAR(50) PRIMARY KEY,       -- ID nhật ký
    employee_id VARCHAR(50),              -- ID nhân viên
    action VARCHAR(100),                  -- Hành động
    target VARCHAR(100),                  -- Đối tượng hành động
    target_id VARCHAR(50),                -- ID đối tượng
    action_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Thời gian hành động
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE
);

-- Tạo bảng Transaction
CREATE TABLE Transaction (
    transaction_id VARCHAR(50) PRIMARY KEY,  -- ID giao dịch
    employee_id VARCHAR(50),                 -- ID nhân viên
    amount DECIMAL(10, 2),                    -- Số tiền giao dịch
    transaction_type VARCHAR(50),             -- Loại giao dịch (Nạp, Rút, Chuyển khoản)
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Thời gian giao dịch
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE
);

-- Tạo bảng Loan
CREATE TABLE Loan (
    loan_id VARCHAR(50) PRIMARY KEY,        -- ID khoản vay
    employee_id VARCHAR(50),                -- ID nhân viên
    loan_amount DECIMAL(10, 2),             -- Số tiền vay
    loan_status VARCHAR(50),                -- Trạng thái vay (Đang vay, Đã trả, ...)
    loan_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Thời gian vay
    FOREIGN KEY (employee_id) REFERENCES Employee(employee_id) ON DELETE CASCADE
);

-- Thêm một số dữ liệu mẫu vào bảng Employee
INSERT INTO Employee (employee_id, full_name, username, password, role) VALUES
('EMP001', 'Nguyen Thi Mai', 'mai.nguyen', 'password123', 'Manager'),
('EMP002', 'Tran Quang Hieu', 'hieu.tran', 'password456', 'Staff');

-- Thêm một số dữ liệu mẫu vào bảng Log
INSERT INTO Log (log_id, employee_id, action, target, target_id) VALUES
('LOG001', 'EMP001', 'Create Employee', 'Employee', 'EMP001'),
('LOG002', 'EMP002', 'Create Employee', 'Employee', 'EMP002');

-- Thêm một số dữ liệu mẫu vào bảng Transaction
INSERT INTO Transaction (transaction_id, employee_id, amount, transaction_type) VALUES
('T001', 'EMP001', 5000, 'Deposit'),
('T002', 'EMP002', 3000, 'Withdraw');

-- Thêm một số dữ liệu mẫu vào bảng Loan
INSERT INTO Loan (loan_id, employee_id, loan_amount, loan_status) VALUES
('L001', 'EMP001', 10000, 'Active'),
('L002', 'EMP002', 5000, 'Paid');


