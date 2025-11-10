import tkinter as tk
from tkinter import messagebox

# Create main window
root = tk.Tk()
root.title("Cab/Auto Booking App")
root.geometry("400x320")
root.configure(bg="#e6f0ff")

# Heading
tk.Label(root, text="Cab/Auto Booking", font=("Arial", 16, "bold"), bg="#e6f0ff").pack(pady=10)

# Source input
tk.Label(root, text="Source:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
source_entry = tk.Entry(root, width=30)
source_entry.pack(pady=5)

# Destination input
tk.Label(root, text="Destination:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40)
destination_entry = tk.Entry(root, width=30)
destination_entry.pack(pady=5)

# Cab type (Listbox)
tk.Label(root, text="Select Cab Type:", font=("Arial", 11), bg="#e6f0ff").pack(anchor="w", padx=40, pady=(10, 0))
cab_listbox = tk.Listbox(root, height=4, selectmode="single")
cab_listbox.insert(1, "Go")
cab_listbox.insert(2, "Sedan")
cab_listbox.insert(3, "XL")
cab_listbox.insert(4, "Prime")
cab_listbox.pack(padx=60, pady=5, fill="x")

# Submit Function (no validation/authentication)
def submit_booking():
    source = source_entry.get()
    destination = destination_entry.get()
    selected = cab_listbox.curselection()
    cab_type = cab_listbox.get(selected) if selected else "Not Selected"

    messagebox.showinfo("Booking Details", f"Source: {source}\nDestination: {destination}\nCab Type: {cab_type}")

# Submit Button
tk.Button(root, text="Submit", width=12, bg="#4CAF50", fg="white", command=submit_booking).pack(pady=20)

# Run the app
root.mainloop()
