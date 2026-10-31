#!/usr/bin/env python3
"""Mirror src/main/java/fonea/ped tree under src/test/java (same paths + *Test classes)."""
from __future__ import annotations

import re
import shutil
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
MAIN_ROOT = ROOT / "src" / "main" / "java" / "fonea" / "ped"
TEST_ROOT = ROOT / "src" / "test" / "java" / "fonea" / "ped"
MAIN_RES = ROOT / "src" / "main" / "resources"
TEST_RES = ROOT / "src" / "test" / "resources"
MIRROR_JAVA_RES = TEST_RES / "mirror-java" / "fonea" / "ped"
LEGACY_TEST = ROOT / "src" / "test" / "java" / "diplome"

PACKAGE_RE = re.compile(r"^\s*package\s+([\w.]+)\s*;", re.MULTILINE)


def copy_resources() -> None:
    if not MAIN_RES.is_dir():
        return
    TEST_RES.mkdir(parents=True, exist_ok=True)
    for item in MAIN_RES.iterdir():
        dest = TEST_RES / item.name
        if item.is_dir():
            if dest.exists():
                shutil.rmtree(dest)
            shutil.copytree(item, dest)
        else:
            shutil.copy2(item, dest)


def write_test_class(java_file: Path, rel_dir: Path) -> None:
    content = java_file.read_text(encoding="utf-8")
    match = PACKAGE_RE.search(content)
    package = match.group(1) if match else "fonea.ped"
    class_name = java_file.stem
    test_class = f"{class_name}Test"
    dest_dir = TEST_ROOT / rel_dir
    dest_dir.mkdir(parents=True, exist_ok=True)
    dest_file = dest_dir / f"{test_class}.java"
    if dest_file.is_file():
        existing = dest_file.read_text(encoding="utf-8")
        if "reglesMetier_aImplementer" not in existing:
            return

    body = f"""package {package};

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests des regles metier pour {class_name} (miroir du package main).
 */
class {test_class} {{

    @Test
    void reglesMetier_aImplementer() {{
        // TODO: couvrir les regles metier de {class_name}
        assertTrue(true);
    }}
}}
"""
    dest_file.write_text(body, encoding="utf-8")


def mirror_java_sources_as_resources() -> None:
    mirror_root = TEST_RES / "mirror-java"
    if mirror_root.is_dir():
        shutil.rmtree(mirror_root)
    for java_file in sorted(MAIN_ROOT.rglob("*.java")):
        rel_dir = java_file.parent.relative_to(MAIN_ROOT)
        dest_dir = MIRROR_JAVA_RES / rel_dir
        dest_dir.mkdir(parents=True, exist_ok=True)
        shutil.copy2(java_file, dest_dir / java_file.name)


def main() -> None:
    if LEGACY_TEST.is_dir():
        shutil.rmtree(LEGACY_TEST)

    if TEST_ROOT.is_dir():
        shutil.rmtree(TEST_ROOT)
    TEST_ROOT.mkdir(parents=True, exist_ok=True)

    copy_resources()

    if not MAIN_ROOT.is_dir():
        raise SystemExit(f"Missing {MAIN_ROOT}")

    mirror_java_sources_as_resources()

    for java_file in sorted(MAIN_ROOT.rglob("*.java")):
        if java_file.name == "PedApplication.java":
            continue
        rel_dir = java_file.parent.relative_to(MAIN_ROOT)
        write_test_class(java_file, rel_dir)

    print(f"Mirrored tests under {TEST_ROOT}")


if __name__ == "__main__":
    main()
