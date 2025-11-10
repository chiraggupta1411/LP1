import tkinter as tk
from tkinter import messagebox

# Create main window
root = tk.Tk()
root.title("Sign-Up Window")
root.geometry("350x250")
root.configure(bg="#e6f0ff")

# Heading
tk.Label(root, text="📝 Sign-Up", font=("Arial", 16, "bold"), bg="#e6f0ff").pack(pady=10)

# Username
tk.Label(root, text="Username:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
username_entry = tk.Entry(root, width=30)
username_entry.pack(pady=5)

# Email
tk.Label(root, text="Email:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
email_entry = tk.Entry(root, width=30)
email_entry.pack(pady=5)

# Password
tk.Label(root, text="Password:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
password_entry = tk.Entry(root, width=30, show="*")
password_entry.pack(pady=5)

# Function for Sign-Up button
def signup():
    username = username_entry.get()
    email = email_entry.get()
    password = password_entry.get()

    info = f"Username: {username}\nEmail: {email}\nPassword: {password}"
    messagebox.showinfo("Sign-Up Info", info)

# Sign-Up Button
tk.Button(root, text="Sign Up", width=12, bg="#4CAF50", fg="white", command=signup).pack(pady=15)

# Run the App
root.mainloop()
