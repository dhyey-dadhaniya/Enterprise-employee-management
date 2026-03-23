import { useCallback, useEffect, useState } from "react";
import {
  createEmployee,
  deleteEmployee,
  listEmployees,
  updateEmployee,
  type Employee,
  type EmployeeInput,
} from "./api";

const emptyForm: EmployeeInput = {
  name: "",
  email: "",
  department: "",
  phone: "",
};

export default function App() {
  const [items, setItems] = useState<Employee[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [form, setForm] = useState<EmployeeInput>(emptyForm);
  const [editingId, setEditingId] = useState<number | null>(null);

  const load = useCallback(async () => {
    setError(null);
    setLoading(true);
    try {
      const data = await listEmployees();
      setItems(data);
    } catch (e) {
      setError(e instanceof Error ? e.message : "Failed to load employees");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    void load();
  }, [load]);

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    try {
      if (editingId == null) {
        await createEmployee(form);
      } else {
        await updateEmployee(editingId, form);
        setEditingId(null);
      }
      setForm(emptyForm);
      await load();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Save failed");
    }
  }

  function startEdit(emp: Employee) {
    setEditingId(emp.id);
    setForm({
      name: emp.name,
      email: emp.email,
      department: emp.department ?? "",
      phone: emp.phone ?? "",
    });
  }

  function cancelEdit() {
    setEditingId(null);
    setForm(emptyForm);
  }

  async function onDelete(id: number) {
    if (!confirm("Delete this employee?")) return;
    setError(null);
    try {
      await deleteEmployee(id);
      if (editingId === id) cancelEdit();
      await load();
    } catch (err) {
      setError(err instanceof Error ? err.message : "Delete failed");
    }
  }

  return (
    <div className="page">
      <header className="header">
        <h1>Employees</h1>
      </header>

      {error && <div className="banner error">{error}</div>}

      <section className="card">
        <h2>{editingId == null ? "Add employee" : `Edit #${editingId}`}</h2>
        <form className="form" onSubmit={onSubmit}>
          <label>
            Name
            <input
              required
              maxLength={100}
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
            />
          </label>
          <label>
            Email
            <input
              required
              type="email"
              maxLength={150}
              value={form.email}
              onChange={(e) => setForm({ ...form, email: e.target.value })}
            />
          </label>
          <label>
            Department
            <input
              maxLength={50}
              value={form.department}
              onChange={(e) => setForm({ ...form, department: e.target.value })}
            />
          </label>
          <label>
            Phone
            <input
              maxLength={20}
              value={form.phone}
              onChange={(e) => setForm({ ...form, phone: e.target.value })}
            />
          </label>
          <div className="actions">
            <button type="submit">{editingId == null ? "Create" : "Update"}</button>
            {editingId != null && (
              <button type="button" className="ghost" onClick={cancelEdit}>
                Cancel
              </button>
            )}
          </div>
        </form>
      </section>

      <section className="card">
        <div className="row">
          <h2>Directory</h2>
          <button type="button" className="ghost small" onClick={() => void load()} disabled={loading}>
            Refresh
          </button>
        </div>
        {loading ? (
          <p>Loading…</p>
        ) : items.length === 0 ? (
          <p className="muted">No employees yet.</p>
        ) : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Department</th>
                  <th>Phone</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {items.map((e) => (
                  <tr key={e.id}>
                    <td>{e.id}</td>
                    <td>{e.name}</td>
                    <td>{e.email}</td>
                    <td>{e.department ?? "—"}</td>
                    <td>{e.phone ?? "—"}</td>
                    <td className="cell-actions">
                      <button type="button" className="ghost small" onClick={() => startEdit(e)}>
                        Edit
                      </button>
                      <button type="button" className="danger small" onClick={() => void onDelete(e.id)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </div>
  );
}
