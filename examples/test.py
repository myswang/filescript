import pathlib
import subprocess
import difflib

EXAMPLES_DIR = pathlib.Path("examples")
JAVA_CMD = [
    "java",
    "--enable-preview",
    "-cp",
    "out/production/Group4Project1:lib/antlr-4.13.2-complete.jar",
    "ui.Main",
]

GREEN, RED, RESET = "\033[92m", "\033[91m", "\033[0m"


def get_test_folders():
    """Returns a list of valid test folders containing input.fs and output.txt."""
    return [
        folder
        for folder in EXAMPLES_DIR.iterdir()
        if folder.is_dir()
        and (folder / "input.fs").exists()
        and (folder / "output.txt").exists()
    ]


def read_file_trimmed(file_path):
    """Reads a file and trims all trailing whitespace from each line."""
    return "\n".join(
        line.rstrip() for line in file_path.read_text(encoding="utf-8").splitlines()
    )


def run_test(folder):
    """Runs the Java command on input.fs and compares output with output.txt."""
    input_path, output_path = folder / "input.fs", folder / "output.txt"

    test_error_msg = None

    try:
        result = subprocess.run(
            JAVA_CMD + [str(input_path)], capture_output=True, text=True, check=True
        )
        java_output = "\n".join(line.rstrip() for line in result.stdout.splitlines())
    except subprocess.CalledProcessError as e:
        test_error_msg = e
        java_output = "\n".join(
            line.rstrip() for line in (e.stdout + e.stderr).splitlines()
        )

    expected_output = read_file_trimmed(output_path)

    if java_output == expected_output:
        print(f"{GREEN}[PASS] {folder.name}{RESET}")
    else:
        print(f"{RED}[FAIL] {folder.name}{RESET}\nDiff:")
        print(f"{RED}Error running test {folder.name}: {test_error_msg}{RESET}")
        diff = difflib.unified_diff(
            expected_output.splitlines(),
            java_output.splitlines(),
            fromfile="Expected",
            tofile="Actual",
            lineterm="",
        )
        print("\n".join(diff))


def main():
    test_folders = get_test_folders()
    if not test_folders:
        print("No valid tests found.")
        return

    print(f"Running {len(test_folders)} tests...")
    for folder in test_folders:
        run_test(folder)


if __name__ == "__main__":
    main()
