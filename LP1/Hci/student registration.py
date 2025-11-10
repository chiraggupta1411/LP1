import tkinter as tk
from tkinter import messagebox

# Create main window
root = tk.Tk()
root.title("Student Registration Form")
root.geometry("400x300")
root.configure(bg="#e6f0ff")

# Heading
tk.Label(root, text="🎓 Student Registration Form", font=("Arial", 14, "bold"), bg="#e6f0ff").pack(pady=10)

# Name
tk.Label(root, text="Full Name:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
name_entry = tk.Entry(root, width=30)
name_entry.pack(pady=5)

# Age
tk.Label(root, text="Age:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
age_entry = tk.Entry(root, width=30)
age_entry.pack(pady=5)

# Roll No
tk.Label(root, text="Roll No.:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
roll_entry = tk.Entry(root, width=30)
roll_entry.pack(pady=5)

# Course
tk.Label(root, text="Course:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
course_entry = tk.Entry(root, width=30)
course_entry.pack(pady=5)

# Submit Function
def submit():
    name = name_entry.get()
    age = age_entry.get()
    roll = roll_entry.get()
    course = course_entry.get()

    info = (
        f"Name: {name}\n"
        f"Age: {age}\n"
        f"Roll No.: {roll}\n"
        f"Course: {course}"
    )

    messagebox.showinfo("Student Details", info)

# Submit Button
tk.Button(root, text="Submit", width=12, bg="#4CAF50", fg="white", command=submit).pack(pady=20)

# Run the App
root.mainloop()
