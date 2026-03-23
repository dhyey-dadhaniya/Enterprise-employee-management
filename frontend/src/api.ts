export type Employee = {
  id: number;
  name: string;
  email: string;
  department: string | null;
  phone: string | null;
};

export type EmployeeInput = {
  name: string;
  email: string;
  department: string;
  phone: string;
};

const base = () => (import.meta.env.VITE_API_URL || "").replace(/\/$/, "");

async function handle(res: Response) {
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || res.statusText);
  }
  if (res.status === 204) return null;
  return res.json();
}

export async function listEmployees(): Promise<Employee[]> {
  const res = await fetch(`${base()}/api/employees`);
  return handle(res) as Promise<Employee[]>;
}

export async function getEmployee(id: number): Promise<Employee> {
  const res = await fetch(`${base()}/api/employees/${id}`);
  return handle(res) as Promise<Employee>;
}

export async function createEmployee(body: EmployeeInput): Promise<Employee> {
  const res = await fetch(`${base()}/api/employees`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });
  return handle(res) as Promise<Employee>;
}

export async function updateEmployee(id: number, body: EmployeeInput): Promise<Employee> {
  const res = await fetch(`${base()}/api/employees/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });
  return handle(res) as Promise<Employee>;
}

export async function deleteEmployee(id: number): Promise<void> {
  const res = await fetch(`${base()}/api/employees/${id}`, { method: "DELETE" });
  await handle(res);
}
