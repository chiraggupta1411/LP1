import tkinter as tk
from tkinter import messagebox

# Create the main window
root = tk.Tk()
root.title("Patient Registration Form")
root.geometry("400x400")
root.configure(bg="#e6f0ff")

# Heading
heading = tk.Label(root, text="🏥 Patient Registration Form", font=("Arial", 14, "bold"), bg="#e6f0ff")
heading.pack(pady=10)

# Patient Name
tk.Label(root, text="Full Name:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
name_entry = tk.Entry(root, width=30)
name_entry.pack(pady=5)

# Age
tk.Label(root, text="Age:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
age_entry = tk.Entry(root, width=30)
age_entry.pack(pady=5)

# Contact
tk.Label(root, text="Contact No:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
contact_entry = tk.Entry(root, width=30)
contact_entry.pack(pady=5)

# Address (Text box)
tk.Label(root, text="Address:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
address_text = tk.Text(root, width=30, height=3)
address_text.pack(pady=5)

# Checkbuttons (e.g., conditions)
insurance_var = tk.IntVar()
allergy_var = tk.IntVar()

tk.Checkbutton(root, text="Has Insurance", variable=insurance_var, bg="#e6f0ff").pack(anchor="w", padx=40, pady=2)
tk.Checkbutton(root, text="Has Allergies", variable=allergy_var, bg="#e6f0ff").pack(anchor="w", padx=40, pady=2)

# Function for Submit button
def submit_form():
    name = name_entry.get()
    age = age_entry.get()
    contact = contact_entry.get()
    address = address_text.get("1.0", tk.END)
    insurance = "Yes" if insurance_var.get() else "No"
    allergies = "Yes" if allergy_var.get() else "No"

    # Just display info (no validation)
    messagebox.showinfo(
        "Submitted",
        f"Patient Registered:\n\n"
        f"Name: {name}\nAge: {age}\nContact: {contact}\n\n"
        f"Insurance: {insurance}\nAllergies: {allergies}\n\n"
        f"Address:\n{address}"
    )

# Submit Button
tk.Button(root, text="Submit", width=12, bg="#4CAF50", fg="white", command=submit_form).pack(pady=15)

# Run the app
root.mainloop()
