# login_basic.py
"""
Basic Login Window (tkinter)
- Username and Password fields
- Password is masked
- No validation: Login button just shows entered values
- Clear button clears the fields
"""

import tkinter as tk
from tkinter import messagebox

def on_login():
    user = username_entry.get()
    pwd = password_entry.get()
    # No validation — just show the values
    messagebox.showinfo("Login Info", f"Username: {user}\nPassword: {pwd}")

def on_clear():
    username_entry.delete(0, tk.END)
    password_entry.delete(0, tk.END)

# Create main window
root = tk.Tk()
root.title("Login")
root.geometry("350x200")
root.resizable(False, False)
root.configure(padx=20, pady=20)

# Title
tk.Label(root, text="🔐 Login", font=("Arial", 16, "bold")).pack(pady=(0,12))

# Username
tk.Label(root, text="Username:", anchor="w").pack(fill="x")
username_entry = tk.Entry(root, width=30)
username_entry.pack(pady=(0,8))

# Password
tk.Label(root, text="Password:", anchor="w").pack(fill="x")
password_entry = tk.Entry(root, width=30, show="*")
password_entry.pack(pady=(0,12))

# Buttons
btn_frame = tk.Frame(root)
btn_frame.pack()

tk.Button(btn_frame, text="Login", width=10, command=on_login).grid(row=0, column=0, padx=6)
tk.Button(btn_frame, text="Clear", width=10, command=on_clear).grid(row=0, column=1, padx=6)

# Start the app
root.mainloop()
