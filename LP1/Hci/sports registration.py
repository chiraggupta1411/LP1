import tkinter as tk
from tkinter import messagebox

# Create the main window
root = tk.Tk()
root.title("Sports Academy Registration")
root.geometry("400x300")
root.configure(bg="#e6f0ff")  # light blue background

# Heading
heading = tk.Label(root, text="🏅 Sports Academy Registration", font=("Arial", 14, "bold"), bg="#e6f0ff")
heading.pack(pady=10)

# Name
tk.Label(root, text="Full Name:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40, pady=2)
name_entry = tk.Entry(root, width=30)
name_entry.pack(pady=2)

# Age
tk.Label(root, text="Age:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40, pady=2)
age_entry = tk.Entry(root, width=30)
age_entry.pack(pady=2)

# Sport
tk.Label(root, text="Sport:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40, pady=2)
sport_entry = tk.Entry(root, width=30)
sport_entry.pack(pady=2)

# Contact
tk.Label(root, text="Contact No:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40, pady=2)
contact_entry = tk.Entry(root, width=30)
contact_entry.pack(pady=2)

# Function for Submit button
def submit_form():
    name = name_entry.get()
    age = age_entry.get()
    sport = sport_entry.get()
    contact = contact_entry.get()

    if name == "" or age == "" or sport == "" or contact == "":
        messagebox.showwarning("Incomplete", "Please fill all fields!")
    else:
        messagebox.showinfo("Submitted", f"Registration Successful!\n\nName: {name}\nAge: {age}\nSport: {sport}\nContact: {contact}")
        clear_form()

# Function for Clear button
def clear_form():
    name_entry.delete(0, tk.END)
    age_entry.delete(0, tk.END)
    sport_entry.delete(0, tk.END)
    contact_entry.delete(0, tk.END)

# Buttons
tk.Button(root, text="Submit", width=10, bg="#4CAF50", fg="white", command=submit_form).pack(pady=8)
tk.Button(root, text="Clear", width=10, bg="#0078D7", fg="white", command=clear_form).pack(pady=3)
tk.Button(root, text="Exit", width=10, bg="#999999", fg="white", command=root.destroy).pack(pady=3)

# Run the application
root.mainloop()
