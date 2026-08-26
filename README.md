# BÀI 4: LẬP TRÌNH LỚP PHÒNG VỆ — BẢO MẬT SQL & CHỐNG TRÀN TOKEN CHO `execute_sql_query` TOOL

## 📌 1. Các Lớp Phòng VệAn Ninh SQL MCP Tool (`SafeSqlExecutorMcpTool`)

Khi cung cấp Tool cho AI Agent tương tác với Database:
1. **Chống SQL Injection & Phá hoại CSDL**:
   - Chặn đứng các câu lệnh hủy hoại/chỉnh sửa dữ liệu: `DROP`, `DELETE`, `UPDATE`, `INSERT`, `ALTER`, `TRUNCATE`.
   - Chặn tuyệt đối kỹ thuật chèn nhiều câu lệnh qua dấu chấm phẩy `;`.
2. **Chống Tràn Cửa Sổ Ngữ Cảnh Token (Token Window Overflow Guard)**:
   - Tự động bổ sung mệnh đề `LIMIT 100` nếu câu lệnh `SELECT` của LLM không có tham số giới hạn.
   - Tránh việc LLM vô tình lấy ra hàng vạn dòng làm sập bộ nhớ Context Window và ngốn chi phí Token khổng lồ.
"# 213_SS12_HW4" 
